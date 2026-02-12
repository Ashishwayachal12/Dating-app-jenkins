package com.mydating.dating.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mydating.dating.dao.UserDao;
import com.mydating.dating.dto.MatchingUser;
import com.mydating.dating.entity.User;
import com.mydating.dating.util.UserGender;
import com.mydating.dating.util.UserSorting;

/**
 * Service class responsible for handling user-related business logic.
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * Saves a new user to the database.
	 *
	 * @param user the user entity to save
	 * @return ResponseEntity with created user and HTTP status
	 */
	public ResponseEntity<?> saveUser(User user) {
		User userSaved = userDao.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
	}

	/**
	 * Retrieves all male users from the database.
	 *
	 * @return ResponseEntity containing list of male users or error message
	 */
	public ResponseEntity<?> findAllmale() {
		List<User> maleUsers = userDao.findAllMaleUser();
		if (maleUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No male user found...");
		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(maleUsers);
		}
	}

	/**
	 * Retrieves all female users from the database.
	 *
	 * @return ResponseEntity containing list of female users or error message
	 */
	public ResponseEntity<?> findAllFemale() {
		List<User> femaleUsers = userDao.findAllFemale();
		if (femaleUsers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No female user found...");
		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(femaleUsers);
		}
	}

	/**
	 * Finds best matching users based on age difference and common interests.
	 *
	 * @param id  the ID of the user to match against
	 * @param top the number of top matches to return
	 * @return ResponseEntity containing list of matching users or error message
	 */
	public ResponseEntity<?> findBestMatch(int id, int top) {
		Optional<User> optional = userDao.findUserById(id);

		if (optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user " + id + " ,unable to find...");
		}

		User user = optional.get();
		List<User> oppositeGenderUsers;

		// Fetch users of the opposite gender
		if (user.getGender().equals(UserGender.MALE)) {
			oppositeGenderUsers = userDao.findAllFemale();
		} else {
			oppositeGenderUsers = userDao.findAllMaleUser();
		}

		List<MatchingUser> matchingUsers = new ArrayList<>();

		for (User u : oppositeGenderUsers) {
			MatchingUser mu = new MatchingUser();
			mu.setId(u.getId());
			mu.setName(u.getName());
			mu.setEmail(u.getEmail());
			mu.setPhone(u.getPhone());
			mu.setAge(u.getAge());
			mu.setIntrests(u.getIntrests());
			mu.setGender(u.getGender());

			// Calculate age difference
			mu.setAgeDiff(Math.abs(user.getAge() - u.getAge()));

			// Count matching interests
			int mic = 0;
			for (String interest : user.getIntrests()) {
				if (u.getIntrests().contains(interest)) {
					mic++;
				}
			}
			mu.setMic(mic);

			matchingUsers.add(mu);
		}

		// Sort users based on custom comparator.
		Collections.sort(matchingUsers, new UserSorting());

		// Return only top N matches.
		List<MatchingUser> result = new ArrayList<>();
		for (MatchingUser mu : matchingUsers) {
			if (top == 0) {
				break;
			}
			result.add(mu);
			top--;
		}

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public ResponseEntity<?> searchByName(String letters) {

		List<User> users = userDao.searchByName("%" + letters + "%");
		if (users.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No  user found with letters:" + letters);
		} else {
			return ResponseEntity.status(HttpStatus.FOUND).body(users);
		}

	}

	
}
