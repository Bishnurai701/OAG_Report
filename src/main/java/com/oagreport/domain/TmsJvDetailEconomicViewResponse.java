package com.oagreport.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TmsJvDetailEconomicViewResponse {
	private BigDecimal id;
	private BigDecimal jvId;
	private String txnType;
	private String economicCode;
	private String economiceEdescription;
	private String economicNdescription;
	private String headEdescription;
	private String headNdescription;
	private String donorGrpCode;
	private String donorGrpEdescription;
	private String donorGrpNdescription;
	private String donorCode;
	private String donorEdescription;
	private String donorNdescription;
	private String sourceTypeCode;
	private String sourceTypeEdescription;
	private String sourceTypeNdescription;
	private String sourceCode;
	private String sourceEdescription;
	private String sourceNdescription;
	private BigDecimal drAmount;
	private BigDecimal crAmount;

	public TmsJvDetailEconomicViewResponse(BigDecimal id, BigDecimal jvId, String txnType, String economicCode,
			String economiceEdescription, String economicNdescription, String headEdescription, String headNdescription,
			String donorGrpCode, String donorGrpEdescription, String donorGrpNdescription, String donorCode,
			String donorEdescription, String donorNdescription, String sourceTypeCode, String sourceTypeEdescription,
			String sourceTypeNdescription, String sourceCode, String sourceEdescription, String sourceNdescription,
			BigDecimal drAmount, BigDecimal crAmount) {
		this.id = id;
		this.jvId = jvId;
		this.txnType = txnType;
		this.economicCode = economicCode;
		this.economiceEdescription = economiceEdescription;
		this.economicNdescription = economicNdescription;
		this.headEdescription = headEdescription;
		this.headNdescription = headNdescription;
		this.donorGrpCode = donorGrpCode;
		this.donorGrpEdescription = donorGrpEdescription;
		this.donorGrpNdescription = donorGrpNdescription;
		this.donorCode = donorCode;
		this.donorEdescription = donorEdescription;
		this.donorNdescription = donorNdescription;
		this.sourceTypeCode = sourceTypeCode;
		this.sourceTypeEdescription = sourceTypeEdescription;
		this.sourceTypeNdescription = sourceTypeNdescription;
		this.sourceCode = sourceCode;
		this.sourceEdescription = sourceEdescription;
		this.sourceNdescription = sourceNdescription;
		this.drAmount = drAmount;
		this.crAmount = crAmount;
	}
}