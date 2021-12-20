package com.oagreport.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.datatablesrepository.JvViewPrintTreasuryRepository;
import com.oagreport.datatablesrepository.TmsReportResponseRepository;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TmsJVViewPrint;
import com.oagreport.domain.TmsJvAdvanceViewResponse;
import com.oagreport.domain.TmsJvDetailActivityViewResponse;
import com.oagreport.domain.TmsJvDetailEconomicViewResponse;
import com.oagreport.domain.TmsJvPayeeViewResponse;
import com.oagreport.domain.TmsJvViewPrintResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TmsJvReportResponseService {

	final JvViewPrintTreasuryRepository jvViewPrintTreasuryRepository;
	final TmsReportResponseRepository tmsReportResponseRepository;

	public TmsJvReportResponseService(JvViewPrintTreasuryRepository jvViewPrintTreasuryRepository,
			TmsReportResponseRepository tmsReportResponseRepository) {
		this.jvViewPrintTreasuryRepository = jvViewPrintTreasuryRepository;
		this.tmsReportResponseRepository = tmsReportResponseRepository;
	}

	public ServiceResponse print(long id) {

		if (jvViewPrintTreasuryRepository.existsById(id)) {
			TmsJVViewPrint jv = jvViewPrintTreasuryRepository.findById(id).get();

			TmsJvViewPrintResponse jvResponse = new TmsJvViewPrintResponse();
			jvResponse.setAccountId(jv.getAccountId());
			jvResponse.setAgencyId(jv.getAgencyId());
			jvResponse.setAmount(jv.getAmount());
			jvResponse.setEdate(jv.getEdate());
			jvResponse.setNdate(jv.getNdate());
			jvResponse.setFiscalYear(jv.getFiscalYear());
			jvResponse.setFiscalYearId(jv.getFiscalYearId());
			jvResponse.setCreatedBy(jv.getCreatedBy());
			jvResponse.setCreatedOn(jv.getCreatedOn());
			jvResponse.setProjectEdescription(jv.getProjectEdescription());
			jvResponse.setProjectNdescription(jv.getProjectNdescription());
			jvResponse.setJvNo(jv.getJvNo());
			jvResponse.setNarration(jv.getNarration());
			jvResponse.setRemarks(jv.getRemarks());

			List<Object[]> economicObjects = tmsReportResponseRepository.getJvDetailByEconomic(id);
			List<TmsJvDetailEconomicViewResponse> economicItems = economicObjects.stream()
					.map(o -> new TmsJvDetailEconomicViewResponse((BigDecimal) o[0], (BigDecimal) o[1], (String) o[2],
							(String) o[3], (String) o[4], (String) o[5], (String) o[6], (String) o[7], (String) o[8],
							(String) o[9], (String) o[10], (String) o[11], (String) o[12], (String) o[13],
							(String) o[14], (String) o[15], (String) o[16], (String) o[17], (String) o[18],
							(String) o[19], (BigDecimal) o[20], (BigDecimal) o[21]))
					.collect(Collectors.toList());

			jvResponse.setJvDetailEconomic(economicItems);

			List<Object[]> activityObjects = tmsReportResponseRepository.getJvDetailByActivities(id);
			List<TmsJvDetailActivityViewResponse> activityList = activityObjects.stream()
					.map(o -> new TmsJvDetailActivityViewResponse((BigDecimal) o[0], (BigDecimal) o[1],
							(BigDecimal) o[2], (String) o[3], (String) o[4], (String) o[5], (String) o[6],
							(String) o[7], (String) o[8], (String) o[9], (String) o[10], (String) o[11], (String) o[12],
							(String) o[13], (String) o[14], (String) o[15], (String) o[16], (String) o[17],
							(String) o[18], (String) o[19], (String) o[20], (String) o[21], (String) o[22],
							(String) o[23], (String) o[24], (String) o[25], (String) o[26], (BigDecimal) o[27],
							(BigDecimal) o[28]))
					.collect(Collectors.toList());

			jvResponse.setJvDetailActivity(activityList);

			List<Object[]> payeeObjects = tmsReportResponseRepository.getJvDetailByPayee(id);
			List<TmsJvPayeeViewResponse> payeeList = payeeObjects.stream()
					.map(o -> new TmsJvPayeeViewResponse((BigDecimal) o[0], (BigDecimal) o[1], (BigDecimal) o[2],
							(String) o[3], (String) o[4], (String) o[5], (String) o[6], (BigDecimal) o[7],
							(String) o[8], (BigDecimal) o[9]))
					.collect(Collectors.toList());

			jvResponse.setPayees(payeeList);

			List<Object[]> advanceObjects = tmsReportResponseRepository.getJvDetailByAdvances(id);
			List<TmsJvAdvanceViewResponse> advanceList = advanceObjects.stream()
					.map(o -> new TmsJvAdvanceViewResponse((BigDecimal) o[0], (BigDecimal) o[1], (String) o[2],
							(String) o[3], (String) o[4], (String) o[5], (BigDecimal) o[6]))
					.collect(Collectors.toList());

			jvResponse.setAdvances(advanceList);

			Map<String, Object> result = new HashMap<>();
			result.put("jv", jvResponse);
			ServiceResponse response = new ServiceResponse(true, result);
			return response;
		}

		ServiceResponse response = new ServiceResponse(false, "Not Found");
		return response;

	}
}
