package com.nguyenkhang.mobile_store.mapper;

import com.nguyenkhang.mobile_store.dto.response.DailyReportResponse;
import com.nguyenkhang.mobile_store.entity.DailyReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailyReportMapper {
    DailyReportResponse toDailyReportResponse(DailyReport dailyReport);
}
