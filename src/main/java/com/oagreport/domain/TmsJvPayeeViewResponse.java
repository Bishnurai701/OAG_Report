package com.oagreport.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TmsJvPayeeViewResponse {
	private BigDecimal id;
	private BigDecimal jvId;
	private BigDecimal sno;
	private String pan;
	private String payeeNname;
	private String payeeEname;
	private String paymentNmethod;
	private BigDecimal bankNname;
	private String accountno;
	private BigDecimal payableAmount;

	public TmsJvPayeeViewResponse(BigDecimal id, BigDecimal jvId, BigDecimal sno, String pan, String payeeNname,
			String payeeEname, String paymentNmethod, BigDecimal bankNname, String accountno,
			BigDecimal payableAmount) {
		this.id = id;
		this.jvId = jvId;
		this.sno = sno;
		this.pan = pan;
		this.payeeNname = payeeNname;
		this.payeeEname = payeeEname;
		this.paymentNmethod = paymentNmethod;
		this.bankNname = bankNname;
		this.accountno = accountno;
		this.payableAmount = payableAmount;
	}
}