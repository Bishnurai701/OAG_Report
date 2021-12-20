package com.oagreport.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oagreport.datatablesrepository.AccountPayeeRepository;
import com.oagreport.domain.AccountPayee;
import com.oagreport.domain.AgencyRequestDto;

@Service
@Transactional
public class AccountPayeeService {

	final AccountPayeeRepository accountPayeeRepository;

	public AccountPayeeService(AccountPayeeRepository accountPayeeRepository) {
		this.accountPayeeRepository = accountPayeeRepository;

	}

	public Iterable<AccountPayee> FindAll(AgencyRequestDto request) {

		if (request.paymentTypeId == 1) {
			Iterable<AccountPayee> data = accountPayeeRepository.finalAllPayeeACPayee(request.agencyId);
			return data;
		} else {
			Iterable<AccountPayee> data = accountPayeeRepository.finalAllPayeeEFTPayee(request.agencyId);
			return data;
		}
	}

	public Iterable<AccountPayee> GetAllPayee(AgencyRequestDto request) {
		Iterable<AccountPayee> data = accountPayeeRepository.finalAllPayee(request.agencyId);
		return data;
	}

}
