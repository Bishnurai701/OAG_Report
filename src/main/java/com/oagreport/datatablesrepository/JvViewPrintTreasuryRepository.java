package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.TmsJVViewPrint;

@Repository
@Transactional
public  interface JvViewPrintTreasuryRepository extends DataTablesRepository<TmsJVViewPrint, Long>{
    Boolean existsById(long id);
}