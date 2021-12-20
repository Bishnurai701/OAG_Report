package com.oagreport.domain;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class TmsJvViewPrintResponse {
	private Long id;
	private String jvNo;
	private Date edate;
	private String ndate;
	private String voucherEType;
	private String voucherNType;
	private Integer fiscalYearId;
	private String fiscalYear;
	private Integer agencyId;
	private Integer accountId;
	private String projectEdescription;
	private String projectNdescription;
	private String levelEdescription;
	private Double amount;
	private String narration;
	private Long status;
	private String remarks;
	private String statusDesc;
	private String createdBy;
	private Date createdOn;
	private List<TmsJvDetailActivityViewResponse> jvDetailActivity;
	private List<TmsJvDetailEconomicViewResponse> jvDetailEconomic;
	private List<TmsJvPayeeViewResponse> payees;
	private List<TmsJvAdvanceViewResponse> advances;
}
