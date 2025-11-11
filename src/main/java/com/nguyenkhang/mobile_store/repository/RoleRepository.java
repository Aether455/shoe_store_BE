package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
