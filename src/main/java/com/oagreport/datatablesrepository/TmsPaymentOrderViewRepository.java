package com.oagreport.datatablesrepository;

import java.util.Optional;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.TmsPaymentOrderView;



@Repository
@Transactional
public  interface TmsPaymentOrderViewRepository extends DataTablesRepository<TmsPaymentOrderView, Long> {
    Optional<TmsPaymentOrderView> findById(long id);
    Boolean existsById(long id);
}
