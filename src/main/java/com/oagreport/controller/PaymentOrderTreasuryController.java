package com.oagreport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsPaymentOrderView;
import com.oagreport.services.TmsPaymentOrderService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ADMIN') or hasRole('OAG') or hasRole('DTCO') or hasRole('OAG_USER') or hasRole('PTCO')")
@RequestMapping("/api/account/treasury/paymentorder")
public class PaymentOrderTreasuryController {

	@Autowired
	private TmsPaymentOrderService service;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public DataTablesOutput<TmsPaymentOrderView> Index(@RequestParam(name = "agencyId", required = true) Long agencyId,
			@RequestParam(name = "fiscalYearId", required = false) Integer fiscalYearId,
			@RequestParam(name = "accountId", required = false) Long accountId, @RequestBody DataTablesInput input) {
		AgencyRequestDto requestDto = new AgencyRequestDto();
		requestDto.setFiscalYearId(fiscalYearId);
		requestDto.setAccountId(accountId);
		requestDto.setAgencyId(agencyId);
		return service.FindAll(requestDto, input);
	}

	@RequestMapping(value = "/print/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> print(@PathVariable(required = true) long id) {
		ServiceResponse response = service.Print(id);
		if (response.isSuccess()) {
			return ResponseEntity.ok().body(response);
		} else {
			return ResponseEntity.badRequest().body(response);
		}
	}

}
