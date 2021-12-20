package com.oagreport.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TmsJvDetailActivityViewResponse {
	private BigDecimal id;
	private BigDecimal jvId;
	private BigDecimal sno;
	private String txnType;
	private String economicCode;
	private String economiceEdescription;
	private String economicNdescription;
	private String activityCode;
	private String activityEdescription;
	private String activityNdescription;
	private String componentCode;
	private String componentEdescription;
	private String componentNdescription;
	private String headEDescription;
	private String headNdescription;
	private String donorGrpCode;
	private String donorGrpEdescription;
	private String donorGrpNdescription;
	private String donorCode;
	private String donorEdescription;
	private String donorNdescription;
	private String sourcetypeCode;
	private String sourceTypeEdescription;
	private String sourceTypeNdescription;
	private String sourceCode;
	private String sourceEdescription;
	private String sourceNdescription;
	private BigDecimal drAmount;
	private BigDecimal crAmount;

	public TmsJvDetailActivityViewResponse(BigDecimal id, BigDecimal jvId, BigDecimal sno, String txnType,
			String economicCode, String economiceEdescription, String economicNdescription, String activityCode,
			String activityEdescription, String activityNdescription, String componentCode,
			String componentEdescription, String componentNdescription, String headEDescription,
			String headNdescription, String donorGrpCode, String donorGrpEdescription, String donorGrpNdescription,
			String donorCode, String donorEdescription, String donorNdescription, String sourcetypeCode,
			String sourceTypeEdescription, String sourceTypeNdescription, String sourceCode, String sourceEdescription,
			String sourceNdescription, BigDecimal drAmount, BigDecimal crAmount) {
		this.id = id;
		this.jvId = jvId;
		this.sno = sno;
		this.txnType = txnType;
		this.economicCode = economicCode;
		this.economiceEdescription = economiceEdescription;
		this.economicNdescription = economicNdescription;
		this.activityCode = activityCode;
		this.activityEdescription = activityEdescription;
		this.activityNdescription = activityNdescription;
		this.componentCode = componentCode;
		this.componentEdescription = componentEdescription;
		this.componentNdescription = componentNdescription;
		this.headEDescription = headEDescription;
		this.headNdescription = headNdescription;
		this.donorGrpCode = donorGrpCode;
		this.donorGrpEdescription = donorGrpEdescription;
		this.donorGrpNdescription = donorGrpNdescription;
		this.donorCode = donorCode;
		this.donorEdescription = donorEdescription;
		this.donorNdescription = donorNdescription;
		this.sourcetypeCode = sourcetypeCode;
		this.sourceTypeEdescription = sourceTypeEdescription;
		this.sourceTypeNdescription = sourceTypeNdescription;
		this.sourceCode = sourceCode;
		this.sourceEdescription = sourceEdescription;
		this.sourceNdescription = sourceNdescription;
		this.drAmount = drAmount;
		this.crAmount = crAmount;
	}
}
