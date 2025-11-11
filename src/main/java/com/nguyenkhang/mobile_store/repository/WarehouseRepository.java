package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findByProvinceOrderByPriorityAsc(String province);

    @Query(value = "SELECT w.*, " +
                    "ST_Distance_Sphere( " +
                    "ST_SRID(POINT(w.longitude, w.latitude), 4326), " +
                    "ST_SRID(POINT(:orderLongitude, :orderLatitude), 4326) " +
                    ") as distance" +
                    " FROM warehouse w " +
                    "ORDER BY distance ",
            nativeQuery = true)
    List<Warehouse> findAllSortedByDistance(@Param("orderLatitude") double orderLatitude,
                                             @Param("orderLongitude") double orderLongitude
    );
}
