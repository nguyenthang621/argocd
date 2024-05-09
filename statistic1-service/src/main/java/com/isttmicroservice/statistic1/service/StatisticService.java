package com.isttmicroservice.statistic1.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isttmicroservice.statistic1.dto.ResponseDTO;
import com.isttmicroservice.statistic1.dto.SearchDTO;
import com.isttmicroservice.statistic1.dto.StatisticDTO;
import com.isttmicroservice.statistic1.entity.Statistic;
import com.isttmicroservice.statistic1.repository.StatisticRepo;



public interface StatisticService {
	void create(StatisticDTO statisticDTO);

	void update(StatisticDTO statisticDTO);

	void delete(Integer id);

	void deleteAll(List<Integer> ids);

	StatisticDTO get(Integer id);

	ResponseDTO<List<StatisticDTO>> find(SearchDTO searchDTO);
}

@Service
class StatisticServiceImpl implements StatisticService {

	@Autowired
	StatisticRepo statisticRepo;

	@Transactional
	@Override
	public void create(StatisticDTO statisticDTO) {

		ModelMapper mapper = new ModelMapper();

		Statistic statistic = mapper.map(statisticDTO, Statistic.class);

		statisticRepo.save(statistic);

		statisticDTO.setId(statistic.getId());
	}

	@Transactional
	@Override
	public void update(StatisticDTO statisticDTO) {
		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(StatisticDTO.class, Statistic.class)
				.setProvider(p -> statisticRepo.findById(statisticDTO.getId()).orElseThrow(NoResultException::new));

		Statistic statistic = mapper.map(statisticDTO, Statistic.class);
		statisticRepo.save(statistic);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Statistic statistic = statisticRepo.findById(id).orElseThrow(NoResultException::new);
		if (statistic != null) {

			statisticRepo.deleteById(id);
		}
	}

	@Transactional
	@Override
	public void deleteAll(List<Integer> ids) {
		statisticRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public StatisticDTO get(Integer id) {
		return statisticRepo.findById(id).map(statistic -> convert(statistic)).orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseDTO<List<StatisticDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());

		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

		Page<Statistic> page = statisticRepo.find(searchDTO.getValue(), pageable);

		ResponseDTO<List<StatisticDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(statistic -> convert(statistic)).collect(Collectors.toList()));
		return responseDTO;
	}

	private StatisticDTO convert(Statistic statistic) {
		return new ModelMapper().map(statistic, StatisticDTO.class);
	}

}