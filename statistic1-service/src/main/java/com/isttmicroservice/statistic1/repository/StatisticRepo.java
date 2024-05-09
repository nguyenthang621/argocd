package com.isttmicroservice.statistic1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isttmicroservice.statistic1.entity.Statistic;


@Repository
public interface StatisticRepo extends JpaRepository<Statistic, Integer> {

	@Query("SELECT u FROM Statistic u WHERE u.message LIKE :x ")
	Page<Statistic> find(@Param("x") String s, Pageable pageable);

}
