package com.oagreport.services;

import com.oagreport.datatablesrepository.AccountHeadMiscRepository;
import com.oagreport.datatablesrepository.MiscAccountRepository;
import com.oagreport.datatablesrepository.MiscJvDetailViewRepository;
import com.oagreport.datatablesrepository.MiscJvPayeeViewRepository;
import com.oagreport.datatablesrepository.MiscJvViewRepository;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.MiscAccount;
import com.oagreport.domain.MiscJVDetailView;
import com.oagreport.domain.MiscJVPayeeView;
import com.oagreport.domain.MiscJvView;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsJvAdvanceViewResponse;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MiscJvService {

	final PaymentTypeService paymentTypeService;
	final VoucherTypeService voucherTypeService;
	final VoucherSubTypeService voucherSubTypeService;
	final AccountHeadMiscRepository accountHeadMiscRepository;
	final MiscJvViewRepository miscJvViewRepository;
	final AmountTypeService amountTypeService;
	final AdvanceTypeService advanceTypeService;

	final MiscJvDetailViewRepository miscJvDetailViewRepository;
	final MiscJvPayeeViewRepository miscJvPayeeViewRepository;

	final AuthorizationService authorizationService;
	private final DataSource dataSource;
	private JdbcTemplate jdbcPaymentOrderTemplate;
	private SimpleJdbcCall generateBillVoucher;
	private SimpleJdbcCall generatePaymentOrder;
	private SimpleJdbcCall voidJournalVoucher;
	private SimpleJdbcCall generateSalaryVoucher;
	private SimpleJdbcCall generateNikasaVoucher;
	private SimpleJdbcCall generateEFTRefundJV;
	private SimpleJdbcCall generateTSANikasaVoucher;
	private SimpleJdbcCall generatePreviousPeski;
	private SimpleJdbcCall generateIncomeVoucher;
	final ModelMapper modelMapper;

	@PersistenceContext
	private EntityManager em;

	final MiscAccountRepository miscAccountRepository;

	public MiscJvService(MiscJvPayeeViewRepository miscJvPayeeViewRepository,
			MiscJvDetailViewRepository miscJvDetailViewRepository, PaymentTypeService paymentTypeService,
			DataSource dataSource, VoucherTypeService voucherTypeService, VoucherSubTypeService voucherSubTypeService,
			AccountHeadMiscRepository accountHeadMiscRepository, ModelMapper modelMapper,
			AmountTypeService amountTypeService, AdvanceTypeService advanceTypeService,
			AuthorizationService authorizationService, MiscAccountRepository miscAccountRepository,
			MiscJvViewRepository miscJvViewRepository) {
		this.miscJvDetailViewRepository = miscJvDetailViewRepository;
		this.miscJvPayeeViewRepository = miscJvPayeeViewRepository;
		this.paymentTypeService = paymentTypeService;
		this.miscJvViewRepository = miscJvViewRepository;

		this.dataSource = dataSource;
		this.voucherTypeService = voucherTypeService;
		this.voucherSubTypeService = voucherSubTypeService;
		this.accountHeadMiscRepository = accountHeadMiscRepository;

		this.modelMapper = modelMapper;
		this.amountTypeService = amountTypeService;

		this.advanceTypeService = advanceTypeService;

		this.authorizationService = authorizationService;

		this.miscAccountRepository = miscAccountRepository;
	}

	public ServiceResponse getAccount(AgencyRequestDto request) {

		Object accountHeads = accountHeadMiscRepository.findAll();
		Object paymentTypes = paymentTypeService.FindAll();
		Object voucherTypes = voucherTypeService.FindAll();
		Object advanceTypes = advanceTypeService.FindAll();
		Object voucherSubTypes = voucherSubTypeService.FindAll();
		Iterable<MiscAccount> accounts = miscAccountRepository.findAll();
		Map<String, Object> result = new HashMap<>();
		result.put("accountHeads", accountHeads);
		result.put("paymentTypes", paymentTypes);
		result.put("voucherTypes", voucherTypes);
		result.put("voucherSubTypes", voucherSubTypes);
		result.put("advanceTypes", advanceTypes);
		result.put("accounts", accounts);

		ServiceResponse response = new ServiceResponse(true, result);
		return response;
	}


	public DataTablesOutput<MiscJvView> FindAll(AgencyRequestDto request, DataTablesInput input) {
		DataTablesOutput<MiscJvView> data = miscJvViewRepository.findAll(input,
				new AgencywithFiscalYearAccountSpecification(request, input));
		return data;
	}

	public Iterable<MiscJvView> FindAll() {
		Iterable<MiscJvView> data = miscJvViewRepository.findAll();
		return data;
	}

	public ServiceResponse print(long id) {
		if (miscJvViewRepository.existsById(id)) {
			Optional<MiscJvView> jv = miscJvViewRepository.findById(id);
			Iterable<MiscJVDetailView> details = miscJvDetailViewRepository.getJvDetails(id);
			Iterable<MiscJVPayeeView> payees = miscJvPayeeViewRepository.getPayees(id);
			List<Object[]> advanceObjects = miscJvViewRepository.getJvDetailByAdvances(id);
			List<TmsJvAdvanceViewResponse> advanceList = advanceObjects.stream()
					.map(o -> new TmsJvAdvanceViewResponse((BigDecimal) o[0], (BigDecimal) o[1], (String) o[2],
							(String) o[3], (String) o[4], (String) o[5], (BigDecimal) o[6]))
					.collect(Collectors.toList());
			Map<String, Object> result = new HashMap<>();
			result.put("jv", jv);
			result.put("details", details);
			result.put("payees", payees);
			result.put("advances", advanceList);

			ServiceResponse response = new ServiceResponse(true, result);
			return response;
		}

		ServiceResponse response = new ServiceResponse(false, "Not Found");
		return response;

	}

	private class AgencywithFiscalYearAccountSpecification implements Specification<MiscJvView> {
		private AgencyRequestDto requestDto;

		public AgencywithFiscalYearAccountSpecification(AgencyRequestDto request, DataTablesInput input) {
			requestDto = request;
		}

		@Override
		public Predicate toPredicate(Root<MiscJvView> root, CriteriaQuery<?> criteriaQuery,
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
