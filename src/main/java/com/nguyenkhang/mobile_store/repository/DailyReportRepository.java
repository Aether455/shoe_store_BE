package com.nguyenkhang.mobile_store.repository;

import com.nguyenkhang.mobile_store.entity.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyReportRepository extends JpaRepository<DailyReport,Long> {
}
