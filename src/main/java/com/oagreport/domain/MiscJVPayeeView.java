package com.oagreport.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "vw_misc_jv_payee")
public class MiscJVPayeeView implements Serializable {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "SNO")
	private Long sno;

	@Column(name = "jv_id")
	private Long jvId;

	@Column(name = "pan")
	private String pan;

	@Column(name = "payeeNname")
	private String payeeNname;

	@Column(name = "payeeEname")
	private String payeeEname;

	@Column(name = "paymentNmethod")
	private String paymentNmethod;

	@Column(name = "BankNname")
	private String bankNname;

	@Column(name = "ACCOUNTNO")
	private String accountno;

	@Column(name = "PAYABLE_AMOUNT")
	private BigDecimal payableAmount;

	public MiscJVPayeeView() {
	}
}