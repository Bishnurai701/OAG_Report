package com.oagreport.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TmsJvAdvanceViewResponse {
	private BigDecimal jvId;
	private BigDecimal payeeId;
	private String payeeNname;
	private String payeeEname;
	private String code;
	private String pan;
	private BigDecimal amount;

	public TmsJvAdvanceViewResponse(BigDecimal jvId, BigDecimal payeeId, String payeeNname, String payeeEname,
			String code, String pan, BigDecimal amount) {
		this.jvId = jvId;
		this.payeeId = payeeId;
		this.payeeNname = payeeNname;
		this.payeeEname = payeeEname;
		this.code = code;
		this.pan = pan;
		this.amount = amount;
	}
}
