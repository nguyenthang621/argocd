package com.isttmicroservice.userservice.client;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.isttmicroservice.userservice.dto.StatisticDTO;

@FeignClient(name = "statistic-service",fallback = StatisticServiceImpl.class)
public interface StatisticService {

	@PostMapping("/statistic/")
	void create(@RequestBody @Valid StatisticDTO statisticDTO);

}
@Component
class StatisticServiceImpl implements StatisticService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public void create(StatisticDTO statisticDTO) {
		//fallback
		logger.error("Statistic Service is very slow");
	}
	

}
