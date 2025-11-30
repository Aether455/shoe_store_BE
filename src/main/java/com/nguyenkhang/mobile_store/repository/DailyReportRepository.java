package com.nguyenkhang.mobile_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nguyenkhang.mobile_store.entity.DailyReport;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport, Long>, JpaSpecificationExecutor<DailyReport> {}
