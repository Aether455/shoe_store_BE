package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    boolean existsByPhoneNumberAndIdNot(String phoneNUmber,long id);
    boolean existsByPhoneNumber(String phoneNUmber);
    Optional<Customer> findByUserId(long userId);


    @Query("select count(c) from Customer c where DATE(c.createAt) = :date")
    Integer countByCreateAt(@Param("date") LocalDate date);

}
