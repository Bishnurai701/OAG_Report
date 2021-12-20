package com.oagreport.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Sort;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "vw_paymentOrder")
public class TmsPaymentOrderView {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE")
    private Long code;

    @Column(name = "FISCALYEAR_ID")
    private Integer fiscalYearId;

    @Column(name = "FiscalYear")
    private String fiscalYear;

    @Column(name = "AGENCY_ID")
    private Long agencyId;

    @Column(name = "AGENCYCODE")
    private String agencyCode;

    @Column(name = "EDATE")
    private Timestamp edate;

    @Column(name = "NDATE")
    private String ndate;

    @Column(name = "APPROVED_NDATE")
    private String verifiedDate;

    @Column(name = "TxnType")
    private String txnType;

    @Column(name = "request_type_id")
    private Integer requestTypeId;

    @Column(name = "paymentorder_type_id")
    private Integer paymentOrderTypeId;

    @Column(name = "paymentordertype_ndesc")
    private String paymentOrderTypNdesc;


    @Column(name = "voucher_type_id")
    private Integer voucherTypeId;

    @Column(name = "vouchertype_ndesc")
    private String voucherTypeNdesc;

    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "AccountCode")
    private String accountCode;

    @Column(name = "account_EDesc")
    private String accountEdesc;


    @Column(name = "account_NDesc")
    private String accountNdesc;

    @Column(name = "TRANS_NO")
    private Long transNo;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "IS_REGD_TSA")
    private Integer isRegdTsa;

    @Column(name = "REGD_NO")
    private String regdNo;

    @Column(name = "AMOUNT")
    private BigDecimal amount;


    @Column(name = "status")
    private Integer status;

    public TmsPaymentOrderView()
    {

    }
}