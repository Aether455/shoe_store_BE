package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByCustomerId(long customerId);
}
