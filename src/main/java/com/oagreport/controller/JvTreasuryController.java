package com.oagreport.controller;

import javax.validation.Valid;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsJVView;
import com.oagreport.services.JvTreasuryService;
import com.oagreport.services.TmsJvReportResponseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ADMIN') or hasRole('OAG') or hasRole('DTCO') or hasRole('OAG_USER') or hasRole('PTCO')")
@RequestMapping("/api/account/treasury/jv")
public class JvTreasuryController {

	final JvTreasuryService jvService;
	final TmsJvReportResponseService reportResponseService;

	public JvTreasuryController(JvTreasuryService jvService, TmsJvReportResponseService reportResponseService) {
		this.jvService = jvService;
		this.reportResponseService = reportResponseService;

	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public DataTablesOutput<TmsJVView> Index(@RequestParam(name = "agencyId", required = true) Long agencyId,
			@RequestParam(name = "fiscalYearId", required = false) Integer fiscalYearId,
			@RequestParam(name = "accountId", required = false) Long accountId, @RequestBody DataTablesInput input) {
		AgencyRequestDto requestDto = new AgencyRequestDto();
		requestDto.setFiscalYearId(fiscalYearId);
		requestDto.setAccountId(accountId);
		requestDto.setAgencyId(agencyId);
		return jvService.FindAll(requestDto, input);
	}

	@RequestMapping(value = "/print/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Print(@PathVariable(required = true) long id) {
		ServiceResponse response = reportResponseService.print(id);
		if (response.isSuccess()) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping("/loadValues")
	public ResponseEntity<?> loadValues() {
		return ResponseEntity.ok().body(jvService.loadValues());
	}

	@PostMapping("/getAccount")
	public ResponseEntity<?> getAccount(@Valid @RequestBody AgencyRequestDto request) {
		return ResponseEntity.ok().body(jvService.getAccount(request));
	}

	@PostMapping("/getPayee")
	public ResponseEntity<?> getPayee(@Valid @RequestBody AgencyRequestDto request) {
		return ResponseEntity.ok().body(jvService.getPayee(request));
	}

}
