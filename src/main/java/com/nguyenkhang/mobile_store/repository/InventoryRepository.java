package com.nguyenkhang.mobile_store.repository;

import java.util.List;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nguyenkhang.mobile_store.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("select i from Inventory i where i.warehouse.id = :warehouseId")
    List<Inventory> findAllByWarehouseIdForUpdate(@Param("warehouseId") long id);

    List<Inventory> findByWarehouseIdAndProductVariant_IdIn(long warehouseId, Iterable<Long> productVariantId);
}
