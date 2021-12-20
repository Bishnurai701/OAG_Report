package com.oagreport.domain;


import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class TmsPaymentOrderViewResponse {
    private Long id;
    private Long code;
    private Integer fiscalYearId;
    private String fiscalYear;
    private Long agencyId;
    private String agencyCode;
    private Timestamp edate;
    private String ndate;
    private String verifiedDate;
    private String txnType;
    private Integer requestTypeId;
    private Integer paymentOrderTypeId;
    private String paymentOrderTypNdesc;
    private Long accountId;
    private String accountCode;
    private String accountEdesc;
    private String accountNdesc;
    private Long transNo;
    private String remarks;
    private Integer isRegdTsa;
    private String regdNo;
    private BigDecimal amount;
    private List<TmsPaymentOrderDetailViewResponse> activities;
    private Integer status;
}
