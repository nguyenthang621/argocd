package com.isttmicroservice.statistic1.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class StatisticDTO {
	private Integer id;

	private String message;

	private Date createdDate;
}
