package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.PaymentOrderType;

import java.util.Optional;

@Repository
@Transactional
public  interface PaymentOrderTypeRepository extends DataTablesRepository<PaymentOrderType, Long>{
    Optional<PaymentOrderType> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0')+1 from PaymentOrderType a")
    long getMax();
}
