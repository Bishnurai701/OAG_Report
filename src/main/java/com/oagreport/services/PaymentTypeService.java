package com.oagreport.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oagreport.datatablesrepository.PaymentTypeRepository;
import com.oagreport.domain.PaymentType;

@Service
@Transactional
public class PaymentTypeService {

	@Autowired
	PaymentTypeRepository paymentTypeRepository;

	public Iterable<PaymentType> FindAll() {
		Iterable<PaymentType> data = paymentTypeRepository.findAll();
		return data;
	}

}