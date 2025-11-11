package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("select i from Inventory i where i.warehouse.id = :warehouseId")
    List<Inventory> findAllByWarehouseIdForUpdate(@Param("warehouseId")  long id);

    List<Inventory> findByWarehouseIdAndProductVariant_IdIn( long warehouseId, Iterable<Long> productVariantId);
}
