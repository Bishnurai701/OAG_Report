package com.oagreport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.AmountTypeRepository;
import com.oagreport.domain.AmountType;

@Service
@Transactional
public class AmountTypeService {

	@Autowired
	AmountTypeRepository amountTypeRepository;

	public Iterable<AmountType> FindAll() {
		Iterable<AmountType> data = amountTypeRepository.findAll();
		return data;
	}

}