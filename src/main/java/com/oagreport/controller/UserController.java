package com.oagreport.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.oagreport.datatablesrepository.AgencyRepository;
import com.oagreport.datatablesrepository.TreasuryOfficeRepository;
import com.oagreport.domain.Agency;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TreasuryOffice;
import com.oagreport.domain.User;
import com.oagreport.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN') or hasRole('OAG') or hasRole('DTCO') or hasRole('OAG_USER') or hasRole('PTCO')")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TreasuryOfficeRepository treasuryOfficeRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	@GetMapping("/resource/getTreasuryOffice")
	public ServiceResponse getTreasuryOffice(Principal principal) {

		String username = principal.getName();
		Optional<User> currentUser = userRepository.findByUsername(username);
		String oIdentity = currentUser.get().getoIdentity();
		Iterable<TreasuryOffice> treasuryOffices = null;
		Iterable<Agency> agencies = null;

		if (oIdentity.equals("ADMIN") || oIdentity.equals("OAG")) {
			treasuryOffices = treasuryOfficeRepository.findAll();

		}

		if (oIdentity.equals("DTCO") || oIdentity.equals("PTCO")) {
			treasuryOffices = treasuryOfficeRepository.findTreasuryOfficeById(currentUser.get().getOfficeId());
			agencies = agencyRepository.findAgenciesByTreasuryOfficeId(currentUser.get().getOfficeId());
		}

		Map<String, Object> result = new HashMap<>();
		result.put("treasuryOffices", treasuryOffices);
		result.put("agencies", agencies);

		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

	@PostMapping("/resource/getAgencies")
	public ServiceResponse getAgencies(@RequestBody AgencyRequestDto request) {
		Iterable<Agency> agencies = null;
		agencies = agencyRepository.findAgenciesByTreasuryOfficeId(request.getTreasuryOfficeId());
		Map<String, Object> result = new HashMap<>();
		result.put("agencies", agencies);
		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

}
