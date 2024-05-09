package com.isttmicroservice.statistic1.api;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isttmicroservice.statistic1.dto.ResponseDTO;
import com.isttmicroservice.statistic1.dto.SearchDTO;
import com.isttmicroservice.statistic1.dto.StatisticDTO;
import com.isttmicroservice.statistic1.service.StatisticService;


@RestController
@RequestMapping("/statistic")
public class StatisticAPIController {
	@Autowired
	private StatisticService statisticService;

	 private Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	@PostMapping("/")
//	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<StatisticDTO> create(@RequestBody @Valid StatisticDTO statisticDTO) throws IOException {
		logger.info("Statistic Controller : add statistic");
		// goi qua Service
//		try {
//			Thread.sleep(10000);
//		}catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		statisticService.create(statisticDTO);
		return ResponseDTO.<StatisticDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(statisticDTO).build();
	}


	@PutMapping("/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<StatisticDTO> update(@ModelAttribute @Valid StatisticDTO statisticDTO) throws IOException {
		statisticService.update(statisticDTO);
//		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
		return ResponseDTO.<StatisticDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(statisticDTO).build();

	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<StatisticDTO> get(@PathVariable(value = "id") int id) {
		return ResponseDTO.<StatisticDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(statisticService.get(id))
				.build();
	}

	@DeleteMapping("/{id}")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {

		statisticService.delete(id);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@DeleteMapping("/delete/all/{ids}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
		statisticService.deleteAll(ids);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@PostMapping("/search")
//	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<List<StatisticDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
		return statisticService.find(searchDTO);
	}

}
