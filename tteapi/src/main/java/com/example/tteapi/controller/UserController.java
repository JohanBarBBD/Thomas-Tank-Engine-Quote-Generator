package com.example.tteapi.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tteapi.jwt.JWTValidation;
import com.example.tteapi.model.User;
import com.example.tteapi.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

	JWTValidation jwtValidation = new JWTValidation();

	@Autowired
	UserRepository userRepository;

	@PostMapping("/users/login")
	public ResponseEntity<String> handleGoogleAuth(@RequestBody String idToken) {
		idToken = idToken.substring(idToken.indexOf('=') + 1, idToken.indexOf('&'));

		StringBuilder response = new StringBuilder();

		URL url;
		try {
			url = new URL("https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			} else {
				response.append("GET request not successful. Response code: ").append(responseCode);
			}

			connection.disconnect();

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(response.toString());

			String userId = json.getAsString("sub");
			String userEmail = json.getAsString("email");
			String userName = json.getAsString("name");

			String jwt = generateJwtToken(userId, userEmail, userName);

			return ResponseEntity.status(HttpStatus.OK).body(jwt);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(@RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		try {
			List<User> users = new ArrayList<User>();

			userRepository.findAll().forEach(users::add);

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id, @RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		Optional<User> userData = userRepository.findById(id);

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/users/by-email")
	public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email, @RequestParam("jwt") String jwt) {
		boolean isValidToken = jwtValidation.validateToken(jwt);
		if (!isValidToken) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

		Optional<User> userData = userRepository.findByEmail(email);

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			User _user = userRepository
					.save(new User(user.getEmail(), user.getName()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static String generateJwtToken(String userId, String userEmail, String userName) {

		Key key = Keys.hmacShaKeyFor(System.getenv("JWT_SECRET").getBytes());

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 3600000);

		return Jwts.builder()
				.setSubject(userId)
				.claim("email", userEmail)
				.claim("name", userName)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
}
