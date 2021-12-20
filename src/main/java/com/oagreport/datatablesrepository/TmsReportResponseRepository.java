package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oagreport.domain.TmsJVView;

import java.util.List;

@Repository
public  interface TmsReportResponseRepository extends DataTablesRepository<TmsJVView, Long> {

    @Query(value =
            " \n" +
                    "select rownum as Id,jv_id,txn_type,economicCode,economiceEDescription,economicNDescription,headEDescription,headNDescription,\n" +
                    "donorGrpCode,donorGrpEDescription,donorGrpNDescription,donorCode,donorEDescription,donorNdescription,\n" +
                    "sourcetypeCode,sourcetypeEdescription,sourceTypeNdescription,sourceCode,sourceEDescription,sourceNDescription,\n" +
                    "dr_amount,cr_amount " +
                    "from (\n" +
                    "select a.JV_ID,\n" +
                    "f.code economicCode,f.e_description economiceEDescription,f.n_description as economicNDescription,\n" +
                    "h.E_DESCRIPTION AS headEDescription, h.N_DESCRIPTION headNDescription,\n" +
                    "c.Code as donorGrpCode,c.E_description as donorGrpEDescription, c.N_description as donorGrpNDescription,\n" +
                    "d.Code as donorCode,d.e_description as donorEDescription,d.n_description as donorNdescription,\n" +
                    "e.Code as sourcetypeCode,e.e_description as sourcetypeEdescription,e.n_description as sourceTypeNdescription,\n" +
                    "i.Code as sourceCode, i.E_description as sourceEDescription, i.n_DESCRIPTION AS sourceNDescription,a.txn_type as txn_type,\n" +
                    "sum(a.dr_amount) dr_amount,sum(a.cr_amount) cr_amount\n" +
                    "from AC_TMS_JV_DETAIL a,  IFMIS_C_DONOR_GRP c, IFMIS_C_DONOR d, IFMIS_C_SOURCE_TYPE e, IFMIS_C_ECONOMIC f,  AC_TMS_ACCOUNT_HEAD h,\n" +
                    "IFMIS_C_SOURCE i\n" +
                    "WHERE a.donor_grp_id=c.Id(+) and a.donor_id=d.Id(+) and a.source_type_id=e.Id(+) and a.economic_id=f.Id(+)\n" +
                    "and a.Head_Id=h.Id(+) and e.source_id=i.Id(+)\n" +
                    "and a.JV_ID=:jvId \n" +
                    "group by a.JV_ID,\n" +
                    "f.code,f.e_description,f.n_description,\n" +
                    "h.E_DESCRIPTION, h.N_DESCRIPTION,\n" +
                    "c.Code,c.E_description, c.N_description,\n" +
                    "d.Code,d.e_description,d.n_description,\n" +
                    "e.Code,e.e_description,e.n_description,\n" +
                    "i.Code, i.E_description, i.n_DESCRIPTION,a.txn_type  order by a.txn_type desc,f.code)", nativeQuery = true)
    List<Object[]>  getJvDetailByEconomic(@Param("jvId") Long jvId);

    @Query(value =
            "  select a.Id, a.JV_ID,a.SNO,a.txn_type,\n" +
                    "f.code economicCode,f.e_description economiceEDescription,f.n_description as economicNDescription,\n" +
                    "b.code activityCode,b.e_description activityEDescription,b.n_description as activityNDescription,\n" +
                    "g.Code componentCode,g.E_DESCRIPTION componentEDescription,g.N_DESCRIPTION as componentNDescription,\n" +
                    "h.E_DESCRIPTION AS headEDescription, h.N_DESCRIPTION headNDescription,\n" +
                    "c.Code as donorGrpCode,c.E_description as donorGrpEDescription, c.N_description as donorGrpNDescription,\n" +
                    "d.Code as donorCode,d.e_description as donorEDescription,d.n_description as donorNdescription,\n" +
                    "e.Code as sourcetypeCode,e.e_description as sourcetypeEdescription,e.n_description as sourceTypeNdescription,\n" +
                    "i.Code as sourceCode, i.E_description as sourceEDescription, i.n_DESCRIPTION AS sourceNDescription,\n" +
                    "a.dr_amount,a.cr_amount\n" +
                    "from AC_TMS_JV_DETAIL a, IFMIS_C_ACTIVITY b, IFMIS_C_DONOR_GRP c, IFMIS_C_DONOR d, IFMIS_C_SOURCE_TYPE e, IFMIS_C_ECONOMIC f, IFMIS_C_PROJECT_COMPONENT g, AC_TMS_ACCOUNT_HEAD h,\n" +
                    "IFMIS_C_SOURCE i\n" +
                    "WHERE a.JV_ID=:jvId AND a.COMPONENT_ID=g.Id(+) AND a.activity_id=b.Id(+) and a.donor_grp_id=c.Id(+) and a.donor_id=d.Id(+) and a.source_type_id=e.Id(+) and a.economic_id=f.Id(+)\n" +
                    "and a.Head_Id=h.Id and e.source_id=i.Id(+) order by txn_type desc,b.id", nativeQuery = true)
    List<Object[]>  getJvDetailByActivities(@Param("jvId") Long jvId);



    @Query(value =
            " select rownum as Id ,a.jv_id,rownum as Sno,b.pan,a.payee_nname as payeeNname,b.code||'-'||a.payee_name as payeeEname,d.n_description paymentNmethod,b.bank_id as BankNname,a.accountno,a.PAYABLE_AMOUNT from\n" +
                    "(\n" +
                    "select a.jv_id,a.payee_id,a.payee_nname,a.payee_name,a.payment_methodtype_id,a.bank_id,a.accountno,\n" +
                    "sum(a.amount),sum(a.amount) as PAYABLE_AMOUNT,sum(a.amount) deduction_AMOUNT\n" +
                    "from ac_tms_jv_payee a\n" +
                    "group by a.jv_id,a.payee_id,a.payee_nname,a.payee_name,a.payment_methodtype_id,a.bank_id,a.accountno) a, ac_c_Payee b, ifmis_c_bank c,ac_c_payment_method d\n" +
                    "where a.jv_id=:jvId and a.payee_id=b.id and a.bank_id=c.id(+) and a.payment_methodtype_id=d.id", nativeQuery = true)
    List<Object[]>  getJvDetailByPayee(@Param("jvId") Long jvId);


    @Query(value =
            "  SELECT JV_ID,PAYEE_ID,CODE,pan, PAYEE_NAME,PAYEE_NNAME,SUM(ADVANCE_AMOUNT) as Amount FROM AC_TMS_JV_ADVANCE\n" +
                    " where JV_ID=:jvId" +
                    " GROUP BY JV_ID,PAYEE_ID,CODE,pan,PAYEE_NAME,PAYEE_NNAME", nativeQuery = true)
    List<Object[]>  getJvDetailByAdvances(@Param("jvId") Long jvId);




    @Query(value =
            "    select a.Id,a.Code as code,a.fiscalyear_id,e.code as FiscalYear,a.agency_id,h.code as AgencyCode,a.ACCOUNT_ID,a.TRANS_NO,a.edate,a.ndate, g.n_description TxnType,\n" +
                    "1 as request_type_id,\n" +
                    "a.remarks, a.amount, a.PAYMENTORDER_TYPE_ID,f.N_DESCRIPTION AS paymentordertype_ndesc,b.code as AccountCode,\n" +
                    "b.code||'-'||c.E_DESCRIPTION AS account_EDesc,b.code||'-'||c.N_DESCRIPTION AS account_NDesc,DECODE(a.Status,1,'New',2,'Verified',3,'Void',4,'Pending') as STATUS_DESC ,\n" +
                    "a.IS_REGD_TSA,a.REGD_NO,a.status,a.created_by,a.Created_on\n" +
                    "from AC_TMS_PAYMENTORDER a,IFMIS_C_ACCOUNT b,IFMIS_C_PROJECT c, IFMIS_C_FISCALYEAR e, AC_C_PAYMENTORDER_TYPE f, ifmis_c_level g, ifmis_c_agency h\n" +
                    "where  substr(b.Code,-1,1)=g.Id  and a.agency_id=h.id and a.ACCOUNT_ID=b.ID and b.Project_ID=c.Id  and a.paymentorder_type_id=f.id;\n", nativeQuery = true)
    List<Object[]>  getPaymentOrder(@Param("jvId") Long jvId);



    @Query(value =
            "      select a.Id,a.Code as code,a.fiscalyear_id,e.code as FiscalYear,a.agency_id,h.code as AgencyCode,a.ACCOUNT_ID,a.TRANS_NO,a.edate,a.ndate, g.n_description TxnType,\n" +
                    "1 as request_type_id,\n" +
                    "a.remarks, a.amount, a.PAYMENTORDER_TYPE_ID,f.N_DESCRIPTION AS paymentordertype_ndesc,b.code as AccountCode,\n" +
                    "b.code||'-'||c.E_DESCRIPTION AS account_EDesc,b.code||'-'||c.N_DESCRIPTION AS account_NDesc,DECODE(a.Status,1,'New',2,'Verified',3,'Void',4,'Pending') as STATUS_DESC ,\n" +
                    "a.IS_REGD_TSA,a.REGD_NO,a.status,a.created_by,a.Created_on\n" +
                    "from AC_TMS_PAYMENTORDER a,IFMIS_C_ACCOUNT b,IFMIS_C_PROJECT c, IFMIS_C_FISCALYEAR e, AC_C_PAYMENTORDER_TYPE f, ifmis_c_level g, ifmis_c_agency h\n" +
                    "where  substr(b.Code,-1,1)=g.Id  and a.agency_id=h.id and a.ACCOUNT_ID=b.ID and b.Project_ID=c.Id  and a.paymentorder_type_id=f.id;\n", nativeQuery = true)
    List<Object[]>  getPaymentOrderQueue(@Param("jvId") Long jvId);


    @Query(value =
            "      select rownum as SNO, payment_order_id,payee_code,payee_ename,payee_nname,pan,economic_code,component_code,sourcetype_code,payment_methodtype_id,payee_accountno,\n" +
                    "    Activity_Code,donor_grp_code,donor_code,bank_code,voucherno,voucherdate,paymentbucket,commitmentNo,PAYABLE_AMOUNT from(\n" +
                    "            select\n" +
                    "                    a.payment_order_id,a.payee_code,a.payee_ename,a.payee_nname,a.pan,b.code as Economic_Code,h.code as Component_Code,\n" +
                    "            i.code as SourceType_Code,d.code as DonorGrp_Code,a.payment_methodtype_id,a.payee_accountno,c.code as Activity_Code,d.code as Donor_grp_code,e.code as donor_code, g.code as Bank_Code,\n" +
                    "            NULL as voucherNo,NULL as VoucherDate,0 as PaymentBucket,NULL as CommitmentNo,\n" +
                    "            sum(a.payable_amount) as PAYABLE_AMOUNT\n" +
                    "    from AC_TMS_PAYMENTORDER_DETAIL a, IFMIS_C_ECONOMIC b, ifmis_c_activity c, ifmis_c_government d, ifmis_c_donor e,\n" +
                    "    ac_c_payment_method f, ifmis_c_bank g,IFMIS_C_PROJECT_COMPONENT h,ifmis_c_source_type i\n" +
                    "    where a.payment_order_id=:paymentOrderId and a.economic_id=b.id and a.activity_id=c.id and a.donor_grp_id=d.id and a.donor_id=e.id and a.payment_methodtype_id=f.id and\n" +
                    "    a.payee_bank_id=g.id(+) and a.component_id=h.id and a.source_type_id=i.id\n" +
                    "    group by a.payment_order_id,a.payee_code,a.payee_ename,a.payee_nname,a.pan,\n" +
                    "    b.code,h.code,i.code,d.code,a.payment_methodtype_id,a.payee_accountno,c.code,d.code,e.code, g.code,NULL,NULL,0,NULL)\n" +
                    "    where payable_amount>0", nativeQuery = true)
    List<Object[]>  getPaymentOrderDetailQueue(@Param("paymentOrderId") Long paymentOrderId);



    @Query(value =
            "      select rownum as SNO, payment_order_id,payee_code,payee_ename,payee_nname,pan,economic_code,component_code,sourcetype_code,payment_methodtype_id,payee_accountno,\n" +
                    "    Activity_Code,donor_grp_code,donor_code,bank_code,voucherno,voucherdate,paymentbucket,commitmentNo,PAYABLE_AMOUNT from(\n" +
                    "            select\n" +
                    "                    a.payment_order_id,a.payee_code,a.payee_ename,a.payee_nname,a.pan,b.code as Economic_Code,h.code as Component_Code,\n" +
                    "            i.code as SourceType_Code,d.code as DonorGrp_Code,1 as payment_methodtype_id,a.payee_accountno,c.code as Activity_Code,d.code as Donor_grp_code,e.code as donor_code, g.code as Bank_Code,\n" +
                    "            a.voucher_no as voucherNo,NULL as VoucherDate,0 as PaymentBucket,NULL as CommitmentNo,\n" +
                    "            sum(a.payable_amount) as PAYABLE_AMOUNT\n" +
                    "    from AC_TMS_PAYMENTORDER_DETAIL a, IFMIS_C_ECONOMIC b, ifmis_c_activity c, ifmis_c_government d, ifmis_c_donor e,\n" +
                    "    ifmis_c_bank g,IFMIS_C_PROJECT_COMPONENT h,ifmis_c_source_type i\n" +
                    "    where a.payment_order_id=:paymentOrderId and a.economic_id=b.id and a.activity_id=c.id and a.donor_grp_id=d.id and a.donor_id=e.id and\n" +
                    "    a.payee_bank_id=g.id(+) and a.component_id=h.id and a.source_type_id=i.id\n" +
                    "    group by a.payment_order_id,a.payee_code,a.payee_ename,a.payee_nname,a.pan,\n" +
                    "    b.code,h.code,i.code,d.code,1,a.payee_accountno,c.code,d.code,e.code, g.code,a.voucher_no,NULL,0,NULL)"
                    , nativeQuery = true)
    List<Object[]>  getPaymentOrderDetailSamayojanQueue(@Param("paymentOrderId") Long paymentOrderId);


    @Query(value =
            "     select a.Id,\n" +
                    "    a.payment_order_id,a.sno,a.jv_no,a.payable_amount,a.payee_code,a.payee_ename,a.payee_nname,a.pan,\n" +
                    "    b.code as Economic_Code, b.Code ||'-'|| b.E_description as Economic_EDesc, b.code||'-'||b.N_Description as Economic_nDesc,\n" +
                    "    h.code as Component_Code,\n" +
                    "    i.code as SourceType_Code,\n" +
                    "    i.e_description as SOURCETYPE_EDESC,\n" +
                    "    i.n_description as SOURCETYPE_NDESC,\n" +
                    "    d.code as DonorGrp_Code,\n" +
                    "    a.payment_methodtype_id,\n" +
                    "    a.payee_accountno,\n" +
                    "    c.code as Activity_Code, c.code||'-'||c.N_description as Activity_ndesc\n" +
                    ",d.code as Donor_grp_code, d.code||'-'||d.e_description as donor_grp_edesc, d.code||'-'||d.n_description as donor_grp_ndesc\n" +
                    ",e.code as donor_code,e.n_description as donor_ndesc\n" +
                    ",f.n_description as paymentMethod_ndesc, g.code as Bank_Code,g.code||'-'||g.n_description as bank_ndesc,a.remarks,a.status,\n" +
                    "    NULL as voucherNo,\n" +
                    "    NULL as VoucherDate,\n" +
                    "            1 as PaymentBucket,\n" +
                    "    NULL as CommitmentNo\n" +
                    "    from AC_TMS_PAYMENTORDER_DETAIL a, IFMIS_C_ECONOMIC b, ifmis_c_activity c, ifmis_c_government d, ifmis_c_donor e,\n" +
                    "    ac_c_payment_method f, ifmis_c_bank g,IFMIS_C_PROJECT_COMPONENT h,ifmis_c_source_type i\n" +
                    "    where a.economic_id=b.id and a.activity_id=c.id and a.donor_grp_id=d.id(+) and a.donor_id=e.id(+) and a.payment_methodtype_id=f.id(+) and\n" +
                    "    a.payee_bank_id=g.id(+) and a.component_id=h.id(+) and a.source_type_id=i.id", nativeQuery = true)
    List<Object[]>  getPaymentOrderDetailView(@Param("jvId") Long jvId);


    @Query(value =
            "     select a.Id,\n" +
                    "    a.payment_order_id,a.sno,a.jv_no,a.payable_amount,a.payee_code,a.payee_nname,a.pan,\n" +
                    "    b.code as Economic_Code, b.Code ||'-'|| b.E_description as Economic_EDesc, b.code||'-'||b.N_Description as Economic_nDesc,\n" +
                    "    c.code as Activity_Code, c.code||'-'||c.N_description as Activity_ndesc\n" +
                    ",d.code as Donor_grp_code, d.code||'-'||d.e_description as donor_grp_edesc, d.code||'-'||d.n_description as donor_grp_ndesc\n" +
                    ",e.code as donor_code,e.n_description as donor_ndesc\n" +
                    ",f.n_description as paymentMethod_ndesc, g.code as Bank_Code,g.code||'-'||g.n_description as bank_ndesc,a.remarks\n" +
                    "    from AC_TMS_PAYMENTORDER_DETAIL a, IFMIS_C_ECONOMIC b, ifmis_c_activity c, ifmis_c_government d, ifmis_c_donor e,\n" +
                    "    ac_c_payment_method f, ifmis_c_bank g\n" +
                    "    where a.economic_id=b.id and a.activity_id=c.id and a.donor_grp_id=d.id(+) and a.donor_id=e.id(+) and a.payment_methodtype_id=f.id(+) and\n" +
                    "    a.payee_bank_id=g.id(+);", nativeQuery = true)
    List<Object[]>  getPaymentOrderDetailEconomicView(@Param("jvId") Long jvId);



    @Query(value =
            "     select a.Id,\n" +
                    "a.payment_order_id,a.sno,a.jv_no,a.payable_amount,a.payee_code,a.payee_ename,a.payee_nname,a.pan,\n" +
                    "b.code as Economic_Code, b.Code ||'-'|| b.E_description as Economic_EDesc, b.code||'-'||b.N_Description as Economic_nDesc,\n" +
                    "h.code as Component_Code,\n" +
                    "i.code as SourceType_Code,\n" +
                    "i.e_description as SOURCETYPE_EDESC,\n" +
                    "i.n_description as SOURCETYPE_NDESC,\n" +
                    "d.code as DonorGrp_Code,\n" +
                    "a.payment_methodtype_id,\n" +
                    "a.payee_accountno,\n" +
                    "c.code as Activity_Code, c.code||'-'||c.N_description as Activity_ndesc\n" +
                    ", d.code||'-'||d.e_description as donor_grp_edesc, d.code||'-'||d.n_description as donor_grp_ndesc\n" +
                    ",e.code as donor_code,e.n_description as donor_ndesc\n" +
                    ",f.n_description as paymentMethod_ndesc, g.code as Bank_Code,g.code||'-'||g.n_description as bank_ndesc,a.remarks,a.status,\n" +
                    "NULL as voucherNo,\n" +
                    "NULL as VoucherDate,\n" +
                    "0 as PaymentBucket,\n" +
                    "NULL as CommitmentNo\n" +
                    "from AC_TMS_PAYMENTORDER_DETAIL a, IFMIS_C_ECONOMIC b, ifmis_c_activity c, ifmis_c_government d, ifmis_c_donor e,\n" +
                    "ac_c_payment_method f, ifmis_c_bank g,IFMIS_C_PROJECT_COMPONENT h,ifmis_c_source_type i\n" +
                    "where a.payment_order_id=:paymentOrderId and  a.economic_id=b.id and a.activity_id=c.id and a.donor_grp_id=d.id(+) and a.donor_id=e.id(+) and a.payment_methodtype_id=f.id(+) and\n" +
                    "a.payee_bank_id=g.id(+) and a.component_id=h.id(+) and a.source_type_id=i.id", nativeQuery = true)
    List<Object[]>  getPaymentOrderDetailActivityView(@Param("paymentOrderId") Long paymentOrderId);




}
