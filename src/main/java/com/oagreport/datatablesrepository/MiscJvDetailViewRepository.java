package com.oagreport.datatablesrepository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oagreport.domain.MiscJVDetailView;

import java.util.Optional;

@Repository
@Transactional
public  interface MiscJvDetailViewRepository extends DataTablesRepository<MiscJVDetailView, Long>{
    Optional<MiscJVDetailView> findById(long id);
    Boolean existsById(long id);

    @Query(value = "select coalesce(max(a.id), '0') from MiscJVDetailView a")
    long getMax();
    @Query(value =
            " select  a from MiscJVDetailView a where a.jvId=:jvId")
    Iterable<MiscJVDetailView> getJvDetails(@Param("jvId") long jvId);

}
