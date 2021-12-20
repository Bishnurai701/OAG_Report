package com.oagreport.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.oagreport.datatablesrepository.TmsPaymentOrderRepository;
import com.oagreport.datatablesrepository.TmsPaymentOrderViewRepository;
import com.oagreport.datatablesrepository.TmsReportResponseRepository;
import com.oagreport.domain.AgencyRequestDto;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsPaymentOrderDetailViewResponse;
import com.oagreport.domain.TmsPaymentOrderView;
import com.oagreport.domain.TmsPaymentOrderViewResponse;
import com.oagreport.repository.UserRepository;

@Service
@Transactional
public class TmsPaymentOrderService {
	@PersistenceContext
	EntityManager entityManager;

	final UserRepository userRepository;
	final TmsPaymentOrderRepository tmsPaymentOrderRepository;
	final TmsReportResponseRepository tmsReportResponseRepository;
	final TmsPaymentOrderViewRepository tmsPaymentOrderViewRepository;

	public TmsPaymentOrderService(UserRepository userRepository,
			TmsPaymentOrderViewRepository tmsPaymentOrderViewRepository,
			TmsPaymentOrderRepository tmsPaymentOrderRepository,
			TmsReportResponseRepository tmsReportResponseRepository) {
		this.tmsReportResponseRepository = tmsReportResponseRepository;
		this.tmsPaymentOrderRepository = tmsPaymentOrderRepository;
		this.userRepository = userRepository;
		this.tmsPaymentOrderViewRepository = tmsPaymentOrderViewRepository;
	}

	public DataTablesOutput<TmsPaymentOrderView> FindAll(AgencyRequestDto request, DataTablesInput input) {
		DataTablesOutput<TmsPaymentOrderView> data = tmsPaymentOrderViewRepository.findAll(input,
				new AgencywithFiscalYearAccountSpecification(request, input));
		return data;
	}

	public DataTablesOutput<TmsPaymentOrderView> FindAll(DataTablesInput input) {
		DataTablesOutput<TmsPaymentOrderView> data = tmsPaymentOrderViewRepository.findAll(input);
		return data;
	}

	private class AgencywithFiscalYearAccountSpecification implements Specification<TmsPaymentOrderView> {
		private Integer status = 1;
		private AgencyRequestDto requestDto;

		public AgencywithFiscalYearAccountSpecification(AgencyRequestDto request, DataTablesInput input) {
			//String statusFilter = input.getColumn("status").getSearch().getValue();
			String statusFilter ="";
			if (StringUtils.hasText(statusFilter)) {
				status = Integer.parseInt(statusFilter);
			}
			requestDto = request;
		}

		@Override
		public Predicate toPredicate(Root<TmsPaymentOrderView> root, CriteriaQuery<?> criteriaQuery,
				CriteriaBuilder criteriaBuilder) {
			Expression<Long> accountId = root.get("accountId").as(Long.class);
			Expression<Long> agencyId = root.get("agencyId").as(Long.class);
			Expression<Long> fiscalYearId = root.get("fiscalYearId").as(Long.class);
			Expression<Integer> statusId = root.get("status").as(Integer.class);

			Predicate a = criteriaBuilder.and(criteriaBuilder.equal(agencyId, requestDto.getAgencyId()));
			Predicate b = criteriaBuilder.and(criteriaBuilder.equal(fiscalYearId, requestDto.getFiscalYearId()));
			Predicate c = criteriaBuilder.and(criteriaBuilder.equal(accountId, requestDto.getAccountId()));
			return criteriaBuilder.and(a, b, c);
		}
	}

	public ServiceResponse Print(long id) {
		if (tmsPaymentOrderRepository.existsById(id)) {
			TmsPaymentOrderView paymentOrder = tmsPaymentOrderViewRepository.findById(id).get();
			TmsPaymentOrderViewResponse responseData = new TmsPaymentOrderViewResponse();
			responseData.setAccountCode(paymentOrder.getAccountCode());
			responseData.setAccountEdesc(paymentOrder.getAccountEdesc());
			responseData.setAccountId(paymentOrder.getAccountId());
			responseData.setAccountNdesc(paymentOrder.getAccountNdesc());
			responseData.setAgencyCode(paymentOrder.getAgencyCode());
			responseData.setAgencyId(paymentOrder.getAgencyId());
			responseData.setAmount(paymentOrder.getAmount());
			responseData.setCode(paymentOrder.getCode());
			responseData.setEdate(paymentOrder.getEdate());
			responseData.setFiscalYear(paymentOrder.getFiscalYear());
			responseData.setFiscalYearId(paymentOrder.getFiscalYearId());
			responseData.setId(paymentOrder.getId());
			responseData.setIsRegdTsa(paymentOrder.getIsRegdTsa());
			responseData.setNdate(paymentOrder.getNdate());
			responseData.setPaymentOrderTypeId(paymentOrder.getPaymentOrderTypeId());
			responseData.setPaymentOrderTypNdesc(paymentOrder.getPaymentOrderTypNdesc());
			responseData.setRegdNo(paymentOrder.getRegdNo());
			responseData.setRemarks(paymentOrder.getRemarks());
			responseData.setRequestTypeId(paymentOrder.getRequestTypeId());
			responseData.setRemarks(paymentOrder.getRemarks());
			responseData.setStatus(paymentOrder.getStatus());
			responseData.setTransNo(paymentOrder.getTransNo());
			responseData.setTxnType(paymentOrder.getTxnType());
			responseData.setVerifiedDate(paymentOrder.getVerifiedDate());

			List<Object[]> activitiesObjects = tmsReportResponseRepository.getPaymentOrderDetailActivityView(id);
			List<TmsPaymentOrderDetailViewResponse> activitiesList = activitiesObjects.stream()
					.map(o -> new TmsPaymentOrderDetailViewResponse((BigDecimal) o[0], (BigDecimal) o[1],
							(BigDecimal) o[2], (BigDecimal) o[3], (BigDecimal) o[4], (String) o[5], (String) o[6],
							(String) o[7], (String) o[8], (String) o[9], (String) o[10], (String) o[11], (String) o[12],
							(String) o[13], (String) o[14], (String) o[15], (String) o[16], (BigDecimal) o[17],
							(String) o[18], (String) o[19], (String) o[20], (String) o[21], (String) o[22],
							(String) o[23], (String) o[24], (String) o[25], (String) o[26], (String) o[27],
							(String) o[28], (BigDecimal) o[29], (String) o[30], (String) o[31], (BigDecimal) o[32],
							(String) o[33]))
					.collect(Collectors.toList());

			responseData.setActivities(activitiesList);

			Map<String, Object> result = new HashMap<>();
			result.put("paymentOrder", responseData);
			ServiceResponse response = new ServiceResponse(true, result);
			return response;
		}

		ServiceResponse response = new ServiceResponse(false, "Already Found");
		return response;
	}

}
