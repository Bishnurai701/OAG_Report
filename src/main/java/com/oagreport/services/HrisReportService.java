package com.oagreport.services;

import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import com.oagreport.domain.ReportRequestModel;
import com.oagreport.domain.ServiceResponse;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequestScope
public class HrisReportService {
	@PersistenceContext
	EntityManager entityManager;

	public ServiceResponse getDeductionReport(ReportRequestModel request) {
		Map<String, Object> result = new HashMap<>();
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("HRIS.getDeductionReport")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(9, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(10, Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(11, String.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(12, void.class, ParameterMode.REF_CURSOR)
				.setParameter(1, request.getFiscalYearId()).setParameter(2, request.getAgencyId())
				.setParameter(3, request.getAccountId()).setParameter(4, request.getTypeId())
				.setParameter(5, request.getDeductionTypeId()).setParameter(6, request.getMonthId())
				.setParameter(7, request.getDateFrom()).setParameter(8, request.getDateTo())
				.setParameter(9, request.getRefId());
		try {
			query.execute();
			Integer nStatus = (Integer) query.getOutputParameterValue(10);
			String nMessage = (String) query.getOutputParameterValue(11);
			List<Object[]> data = query.getResultList();
			result.put("data", data);
			result.put("status", nStatus);
			result.put("message", nMessage);
		} finally {
			query.unwrap(ProcedureOutputs.class).release();
		}

		StoredProcedureQuery query1 = entityManager.createStoredProcedureQuery("HRIS.getDeductionPaymentReport")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(5, Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(7, void.class, ParameterMode.REF_CURSOR)
				.setParameter(1, request.getAgencyId()).setParameter(2, request.getAccountId())
				.setParameter(3, request.getDeductionTypeId()).setParameter(4, request.getRefId());
		try {
			query1.execute();
			Integer nStatus1 = (Integer) query1.getOutputParameterValue(5);
			String nMessage1 = (String) query1.getOutputParameterValue(6);
			List<Object[]> data1 = query1.getResultList();

			if (nStatus1 == 0) {
				result.put("dataPayment", data1);
			}

		} finally {
			query1.unwrap(ProcedureOutputs.class).release();
		}

		ServiceResponse response = new ServiceResponse(true, "SUCCESS", result);
		return response;

	}

	public ServiceResponse getPaymentDeductionReport(ReportRequestModel request) {
		Map<String, Object> result = new HashMap<>();
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.getBillDeductionReport")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(8, void.class, ParameterMode.REF_CURSOR)
				.setParameter(1, request.getFiscalYearId()).setParameter(2, request.getAgencyId())
				.setParameter(3, request.getAccountId()).setParameter(4, request.getDeductionTypeId())
				.setParameter(5, request.getRefId());
		try {
			query.execute();
			Integer nStatus = (Integer) query.getOutputParameterValue(6);
			String nMessage = (String) query.getOutputParameterValue(7);
			List<Object[]> data = query.getResultList();
			result.put("data", data);
			result.put("status", nStatus);
			result.put("message", nMessage);
		} finally {
			query.unwrap(ProcedureOutputs.class).release();
		}

		ServiceResponse response = new ServiceResponse(true, "SUCCESS", result);
		return response;

	}

	public ServiceResponse getDeductionPaymentReport(ReportRequestModel request) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("HRIS.getDeductionPaymentReport")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
				.setParameter(1, request.getFiscalYearId()).setParameter(2, request.getAgencyId())
				.setParameter(3, request.getDeductionTypeId()).setParameter(4, request.getMonthId())
				.setParameter(5, request.getDateFrom()).setParameter(6, request.getDateTo());

		try {
			query.execute();
			Integer nStatus = (Integer) query.getOutputParameterValue(7);
			String nMessage = (String) query.getOutputParameterValue(8);
			Map<String, Object> result = new HashMap<>();
			List<Object[]> data = query.getResultList();

			if (nStatus == 0) {
				result.put("data", data);
			}

			ServiceResponse response = new ServiceResponse(true, nMessage, result);
			return response;
		} finally {
			query.unwrap(ProcedureOutputs.class).release();
		}
	}

	public ServiceResponse getSalarySheetReport(ReportRequestModel request) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("HRIS.getSalarySheetReport")
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
				.registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
				.registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
				.setParameter(1, request.getFiscalYearId()).setParameter(2, request.getAgencyId())
				.setParameter(3, request.getDeductionTypeId()).setParameter(4, request.getMonthId())
				.setParameter(5, request.getDateFrom()).setParameter(6, request.getDateTo());

		try {
			query.execute();
			Integer nStatus = (Integer) query.getOutputParameterValue(7);
			String nMessage = (String) query.getOutputParameterValue(8);

			Map<String, Object> result = new HashMap<>();
			List<Object[]> data = query.getResultList();

			if (nStatus == 0) {
				result.put("data", data);
			}

			ServiceResponse response = new ServiceResponse(true, nMessage, result);
			return response;
		} finally {
			query.unwrap(ProcedureOutputs.class).release();
		}
	}

}
