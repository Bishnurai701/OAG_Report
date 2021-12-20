package com.oagreport.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AC_TMS_PAYMENTORDER")
public class TmsPaymentOrder extends BaseDto {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "CODE")
	private String code;

	@Column(name = "FISCALYEAR_ID")
	private Integer fiscalYearId;

	@Column(name = "AGENCY_ID")
	private Long agencyId;

	@Column(name = "ACCOUNT_ID")
	private Long accountId;

	@Column(name = "TRANS_NO")
	private String transNo;

	@Column(name = "EDATE")
	private Date edate;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "NDATE")
	private String ndate;

	@Column(name = "IS_REGD_TSA")
	private Integer isRegdTsa;

	@Column(name = "REGD_NO")
	private String regdNo;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "PAYMENTORDER_TYPE_ID")
	private Long paymentOrderTypeId;

	@Column(name = "voucher_type_id")
	private Long voucherTypeId;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "PAYMENT_ORDER_ID")
//    private List<TmsPaymentOrderDetail> details;

	public TmsPaymentOrder() {

	}
}
