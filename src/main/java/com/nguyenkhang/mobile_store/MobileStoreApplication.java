package com.nguyenkhang.mobile_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@EnableScheduling//Bật tính năng tự động chạy theo lịch
@EnableAsync// bật tính năng chạy bất đồng bộ
@SpringBootApplication
public class MobileStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileStoreApplication.class, args);
	}

}
