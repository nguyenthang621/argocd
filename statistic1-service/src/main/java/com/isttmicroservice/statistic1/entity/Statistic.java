package com.isttmicroservice.statistic1.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "statistic")
@EqualsAndHashCode(callSuper = false)
public class Statistic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String message;

	private Date createdDate;
}
