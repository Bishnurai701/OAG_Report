package com.oagreport.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oagreport.domain.ERole;
import com.oagreport.domain.MessageResponse;
import com.oagreport.domain.Password;
import com.oagreport.domain.Role;
import com.oagreport.domain.SignupRequest;
import com.oagreport.domain.User;
import com.oagreport.repository.RoleRepository;
import com.oagreport.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN') or hasRole('OAG')")
public class AdminController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, Principal principal) {
		String username = principal.getName();
		Optional<User> currentUser = userRepository.findByUsername(username);
		if (userRepository.existsByUsername(signUpRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		if (currentUser.get().getoIdentity().equals("ADMIN")) {

			if (signUpRequest.getoIdentity().equals("OAG")) {
				User user = new User();
				// Set<Role> roles = new HashSet<>();
				user.setUsername(signUpRequest.getUserName());
				user.setEmail(signUpRequest.getEmail());
				user.setPhoneNo(signUpRequest.getPhoneNo());
				user.setPassword(encoder.encode(signUpRequest.getPassword()));
				user.setoIdentity("OAG");
				user.setFullName(signUpRequest.getFullName());
				user.setUserOf(username);
				user.setOfficeId(signUpRequest.getOfficeId());
				user.setRoles(new HashSet<>(roleRepository.findByName(ERole.ROLE_OAG)));
				userRepository.save(user);
				return ResponseEntity.ok(new MessageResponse("OAG USER registered successfully!"));
			}

			if (signUpRequest.getoIdentity().equals("DTCO")) {
				User user = new User();
				// Set<Role> roles = new HashSet<>();
				user.setUsername(signUpRequest.getUserName());
				user.setEmail(signUpRequest.getEmail());
				user.setPhoneNo(signUpRequest.getPhoneNo());
				user.setPassword(encoder.encode(signUpRequest.getPassword()));
				user.setoIdentity("DTCO");
				user.setFullName(signUpRequest.getFullName());
				user.setUserOf(username);
				user.setOfficeId(signUpRequest.getOfficeId());
				user.setRoles(new HashSet<>(roleRepository.findByName(ERole.ROLE_DTCO)));
				userRepository.save(user);
				return ResponseEntity.ok(new MessageResponse("DTCO USER registered successfully!"));
			}
			
			if (signUpRequest.getoIdentity().equals("PTCO")) {
				User user = new User();
				// Set<Role> roles = new HashSet<>();
				user.setUsername(signUpRequest.getUserName());
				user.setEmail(signUpRequest.getEmail());
				user.setPhoneNo(signUpRequest.getPhoneNo());
				user.setPassword(encoder.encode(signUpRequest.getPassword()));
				user.setoIdentity("PTCO");
				user.setFullName(signUpRequest.getFullName());
				user.setUserOf(username);
				user.setOfficeId(signUpRequest.getOfficeId());
				user.setRoles(new HashSet<>(roleRepository.findByName(ERole.ROLE_PTCO)));
				userRepository.save(user);
				return ResponseEntity.ok(new MessageResponse("DTCO USER registered successfully!"));
			}

			return ResponseEntity.ok(new MessageResponse("Admin user creation failure!"));

		}

		if (currentUser.get().getoIdentity().equals("OAG")) {

			User user = new User();
			// Set<Role> roles = new HashSet<>();
			user.setUsername(signUpRequest.getUserName());
			System.out.println(signUpRequest.getUserName());
			user.setEmail(signUpRequest.getEmail());
			user.setPhoneNo(signUpRequest.getPhoneNo());
			user.setPassword(encoder.encode(signUpRequest.getPassword()));
			user.setoIdentity("OAG");
			user.setFullName(signUpRequest.getFullName());
			user.setUserOf(username);
			user.setOfficeId(signUpRequest.getOfficeId());
			user.setRoles(new HashSet<>(roleRepository.findByName(ERole.ROLE_OAG_USER)));
			userRepository.save(user);
			return ResponseEntity.ok(new MessageResponse("OAG'S USER registered successfully!"));

		}

		return ResponseEntity.ok(new MessageResponse("User Not registered!"));
	}

	@GetMapping("/userList")
	public ResponseEntity<List<User>> userList(Principal principal) {
		String userName = principal.getName();
		return ResponseEntity.ok().body(userRepository.findByUserOf(userName));
	}

	@PostMapping("/editUser")
	public ResponseEntity<?> editUser(@RequestBody User euser) {
		@SuppressWarnings("deprecation")
		User currentUser = userRepository.getOne(euser.getId());
		if (currentUser == null) {
			return ResponseEntity.ok(new MessageResponse("User Not Found!"));
		} else {
			currentUser.setPhoneNo(euser.getPhoneNo());
			currentUser.setFullName(euser.getFullName());
			userRepository.save(currentUser);
			return ResponseEntity.ok(new MessageResponse("Edit Completed!"));
		}
	}

	

	@PostMapping("/reSetPassword")
	public ResponseEntity<?> reSetPassword(@RequestBody User euser) {
		@SuppressWarnings("deprecation")
		User currentUser = userRepository.getOne(euser.getId());
		if (currentUser == null) {
			return ResponseEntity.ok(new MessageResponse("User Not Found!"));
		} else {
			currentUser.setPassword(encoder.encode(euser.getPassword()));
			userRepository.save(currentUser);
			return ResponseEntity.ok(new MessageResponse("Edit Completed!"));
		}
	}
	
	@PostMapping("/changePassword")
	public Map<String, Object> ChangePassword(Principal principal, @RequestBody Password password) {

		Optional<User> currentUser = userRepository.findByUsername(principal.getName());
		if (currentUser == null) {
			Map<String, Object> result = new HashMap<>();
			result.put("message", "User not Found");
			result.put("status","0");
			return  result;
		} else {
			@SuppressWarnings("deprecation")
			User currentUser1 = userRepository.getOne(currentUser.get().getId());
			// String oldPassword = encoder.encode(password.getCurrentPassword());
			if (encoder.matches(password.getCurrentPassword(), currentUser1.getPassword())) {
				currentUser1.setPassword(encoder.encode(password.getNewPassword()));
				userRepository.save(currentUser1);
				Map<String, Object> result = new HashMap<>();
				result.put("message", "Password Changed Successfully");
				result.put("status","1");
				return  result;
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "Your Old Password doesnot Match");
				result.put("status","0");
				return  result;
			}
		}

	}

	@PostMapping("/deleteUser")
	public ResponseEntity<?> deleteUser(@RequestBody User user, Principal principal) {

		List<User> userList = userRepository.findByUserOf(principal.getName());
		for (int i = 0; i <= userList.size(); i++) {
			if (userList.get(i).getId().equals(user.getId())) {
				userRepository.deleteById(user.getId());
				return ResponseEntity.ok(new MessageResponse("User Deleted!"));
			}
		}
		return ResponseEntity.ok(new MessageResponse("User cant be Deleted!"));
	}

}
