package com.oagreport.datatablesrepository;

import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.TmsPaymentOrder;



@Repository
@Transactional
public  interface TmsPaymentOrderRepository extends DataTablesRepository<TmsPaymentOrder, Long> {
    Optional<TmsPaymentOrder> findById(long id);
//    List<TmsBill> findByAgencyIdAndFiscalYearIdAndBillNo(Long agencyId, Integer fiscalYearId, Long billNo);
//    List<TmsBill> findByAgencyIdAndFiscalYearIdAndPanAndPartyNameAndBillNo(Long agencyId, Integer fiscalYearId, String pan, String partyName, Long billNo);
//    List<TmsBill> findByAgencyIdAndFiscalYearIdAndBalanceAmountGreaterThan(Long agencyId, Integer fiscalYearId, Double amount);
    Boolean existsById(long id);


	/*
	 * @Query(value = "select coalesce(max(a.id), '0') from MiscPaymentOrder a")
	 * long getMax();
	 */

}