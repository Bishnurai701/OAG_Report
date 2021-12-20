package com.oagreport.domain;

import lombok.Data;
import java.math.BigDecimal;
@Data


public class TmsPaymentOrderDetailViewResponse {
    private BigDecimal id;
    private BigDecimal paymentOrderId;
    private BigDecimal sno;
    private BigDecimal jvNo;
    private BigDecimal payableAmount;
    private String payeeCode;
    private String payeeEname;
    private String payeeNname;
    private String pan;
    private String economicCode;
    private String economicEdesc;
    private String economicNdesc;
    private String componentCode;
    private String sourceTypeCode;
    private String sourceTypeEdesc;
    private String sourceTypeNdesc;
    private String donorGrpCode;
    private BigDecimal paymentMethodTypeId;
    private String accountNo;
    private String activityCode;
    private String activityNDesc;
    private String donorGrpEdesc;
    private String donorGrpNdesc;
    private String donorCode;
    private String donorNdesc;
    private String paymentMethodNdesc;
    private String bankCode;
    private String bankNdesc;
    private String remarks;
    private BigDecimal status;
    private String voucherNo;
    private String voucherDate;
    private BigDecimal paymentBucket;
    private String commitmentNo;

    public TmsPaymentOrderDetailViewResponse(BigDecimal id, BigDecimal paymentOrderId, BigDecimal sno, BigDecimal jvNo, BigDecimal payableAmount, String payeeCode, String payeeEname, String payeeNname, String pan, String economicCode, String economicEdesc, String economicNdesc, String componentCode, String sourceTypeCode, String sourceTypeEdesc, String sourceTypeNdesc, String donorGrpCode, BigDecimal paymentMethodTypeId, String accountNo, String activityCode, String activityNDesc, String donorGrpEdesc, String donorGrpNdesc, String donorCode, String donorNdesc, String paymentMethodNdesc, String bankCode, String bankNdesc, String remarks, BigDecimal status, String voucherNo, String voucherDate, BigDecimal paymentBucket, String commitmentNo) {
        this.id = id;
        this.paymentOrderId = paymentOrderId;
        this.sno = sno;
        this.jvNo = jvNo;
        this.payableAmount = payableAmount;
        this.payeeCode = payeeCode;
        this.payeeEname = payeeEname;
        this.payeeNname = payeeNname;
        this.pan = pan;
        this.economicCode = economicCode;
        this.economicEdesc = economicEdesc;
        this.economicNdesc = economicNdesc;
        this.componentCode = componentCode;
        this.sourceTypeCode = sourceTypeCode;
        this.sourceTypeEdesc = sourceTypeEdesc;
        this.sourceTypeNdesc = sourceTypeNdesc;
        this.donorGrpCode = donorGrpCode;
        this.paymentMethodTypeId = paymentMethodTypeId;
        this.accountNo = accountNo;
        this.activityCode = activityCode;
        this.activityNDesc = activityNDesc;
        this.donorGrpEdesc = donorGrpEdesc;
        this.donorGrpNdesc = donorGrpNdesc;
        this.donorCode = donorCode;
        this.donorNdesc = donorNdesc;
        this.paymentMethodNdesc = paymentMethodNdesc;
        this.bankCode = bankCode;
        this.bankNdesc = bankNdesc;
        this.remarks = remarks;
        this.status = status;
        this.voucherNo = voucherNo;
        this.voucherDate = voucherDate;
        this.paymentBucket = paymentBucket;
        this.commitmentNo = commitmentNo;
    }
}
