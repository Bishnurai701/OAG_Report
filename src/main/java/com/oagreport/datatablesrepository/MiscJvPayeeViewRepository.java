package com.oagreport.datatablesrepository;


import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.MiscJVPayeeView;

import java.util.Optional;

@Repository
@Transactional
public  interface MiscJvPayeeViewRepository extends DataTablesRepository<MiscJVPayeeView, Long>{
    Optional<MiscJVPayeeView> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0') from MiscJVPayeeView a")
    long getMax();
    @Query(value =
            " select  a from MiscJVPayeeView a where a.jvId=:jvId")
    Iterable<MiscJVPayeeView> getPayees(@Param("jvId") long jvId);

}