package com.oagreport.services;

import com.oagreport.datatablesrepository.JvViewTreasuryRepository;
import com.oagreport.datatablesrepository.LedgerTypeRepository;
import com.oagreport.datatablesrepository.PaymentOrderTypeRepository;
import com.oagreport.domain.AccountDto;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsJVView;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.sql.DataSource;
import java.util.*;

@Service
@Transactional
public class JvTreasuryService {

	@Autowired
	private PaymentTypeService paymentTypeService;

	final AccountPayeeService payeeService;
	final JvViewTreasuryRepository jvViewRepository;
	final LedgerTypeRepository ledgerTypeRepository;
	final VoucherTypeService voucherTypeService;
	final VoucherSubTypeService voucherSubTypeService;
	final AccountHeadTreasuryService accountHeadService;
	final PaymentOrderTypeRepository paymentOrderTypeRepository;
	final AmountTypeService amountTypeService;
	final AdvanceTypeService advanceTypeService;

	final AuthorizationService authorizationService;
	private final DataSource dataSource;

	final ModelMapper modelMapper;

	@PersistenceContext
	private EntityManager em;

	public JvTreasuryService(PaymentOrderTypeRepository paymentOrderTypeRepository,
			LedgerTypeRepository ledgerTypeRepository, AccountPayeeService payeeService,
			PaymentTypeService paymentTypeService, DataSource dataSource, VoucherTypeService voucherTypeService,
			VoucherSubTypeService voucherSubTypeService, AccountHeadTreasuryService accountHeadService,
			ModelMapper modelMapper, AmountTypeService amountTypeService, AdvanceTypeService advanceTypeService,
			AuthorizationService authorizationService, JvViewTreasuryRepository jvViewRepository) {
		this.ledgerTypeRepository = ledgerTypeRepository;
		this.paymentOrderTypeRepository = paymentOrderTypeRepository;
		this.payeeService = payeeService;
		this.paymentTypeService = paymentTypeService;
		this.jvViewRepository = jvViewRepository;
		this.dataSource = dataSource;
		this.voucherTypeService = voucherTypeService;
		this.voucherSubTypeService = voucherSubTypeService;
		this.accountHeadService = accountHeadService;
		this.modelMapper = modelMapper;
		this.amountTypeService = amountTypeService;
		this.advanceTypeService = advanceTypeService;
		this.authorizationService = authorizationService;

	}

	public ServiceResponse getAccount(AgencyRequestDto request) {

		Object accountHeads = accountHeadService.FindAll();
		Object paymentTypes = paymentTypeService.FindAll();
		Object voucherTypes = voucherTypeService.FindAll();
		Object advanceTypes = advanceTypeService.FindAll();
		Object amountTypes = amountTypeService.FindAll();
		Object voucherSubTypes = voucherSubTypeService.FindAll();

		// List<AccountDto> accountItems= agencyDetailService.getAccount(request);
		List<AccountDto> accountItems = authorizationService.GetBudgetAccount(request);

		Map<String, Object> result = new HashMap<>();
		result.put("accounts", accountItems);

		result.put("accountHeads", accountHeads);

		result.put("paymentTypes", paymentTypes);
		result.put("voucherTypes", voucherTypes);
		result.put("voucherSubTypes", voucherSubTypes);
		result.put("amountTypes", amountTypes);
		result.put("advanceTypes", advanceTypes);

		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

	public DataTablesOutput<TmsJVView> FindAll(AgencyRequestDto request, DataTablesInput input) {
		DataTablesOutput<TmsJVView> data = jvViewRepository.findAll(input,
				new AgencywithFiscalYearAccountSpecification(request, input));
		return data;
	}

	public Iterable<TmsJVView> FindAll() {
		Iterable<TmsJVView> data = jvViewRepository.findAll();
		return data;
	}

	public ServiceResponse getPayee(AgencyRequestDto request) {
		Object payees = payeeService.FindAll(request);
		Object ledgerTypes = ledgerTypeRepository.findAllByStatus();
		Map<String, Object> result = new HashMap<>();
		result.put("payees", payees);
		result.put("ledgerTypes", ledgerTypes);

		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

	public ServiceResponse getAllPayee(AgencyRequestDto request) {
		Object payees = payeeService.GetAllPayee(request);
		Map<String, Object> result = new HashMap<>();
		result.put("payees", payees);
		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

	public ServiceResponse loadValues() {
		Object paymentOrderTypes = paymentOrderTypeRepository.findAll();
		Map<String, Object> result = new HashMap<>();
		result.put("paymentOrderTypes", paymentOrderTypes);
		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}

	private class AgencywithFiscalYearAccountSpecification implements Specification<TmsJVView> {
		private AgencyRequestDto requestDto;

		public AgencywithFiscalYearAccountSpecification(AgencyRequestDto request, DataTablesInput input) {
			requestDto = request;
		}

		@Override
		public Predicate toPredicate(Root<TmsJVView> root, CriteriaQuery<?> criteriaQuery,
				CriteriaBuilder criteriaBuilder) {
			Expression<Long> accountId = root.get("accountId").as(Long.class);
			Expression<Long> agencyId = root.get("agencyId").as(Long.class);
			Expression<Long> fiscalYearId = root.get("fiscalYearId").as(Long.class);

			Predicate a = criteriaBuilder.and(criteriaBuilder.equal(agencyId, requestDto.getAgencyId()));
			Predicate b = criteriaBuilder.and(criteriaBuilder.equal(fiscalYearId, requestDto.getFiscalYearId()));
			Predicate c = criteriaBuilder.and(criteriaBuilder.equal(accountId, requestDto.getAccountId()));
			return criteriaBuilder.and(a, b, c);
		}
	}

	@Data
	public class JVResponseData {
		private Long jvId;
		private String jvNo;
		private Integer agencyId;
		private Integer accountId;
		private Integer fiscalYearId;
	}
}