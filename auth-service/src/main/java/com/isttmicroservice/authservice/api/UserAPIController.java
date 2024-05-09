package com.isttmicroservice.authservice.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.isttmicroservice.authservice.client.NotificationService;
import com.isttmicroservice.authservice.client.StatisticService;
import com.isttmicroservice.authservice.dto.MessageDTO;
import com.isttmicroservice.authservice.dto.ResponseDTO;
import com.isttmicroservice.authservice.dto.SearchDTO;
import com.isttmicroservice.authservice.dto.StatisticDTO;
import com.isttmicroservice.authservice.dto.UserDTO;

import javax.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAPIController {
	@Autowired
	private com.isttmicroservice.authservice.service.UserService userService;
	
	@Autowired
	private StatisticService statisticService;
	@Autowired
	 NotificationService notificationService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping("/")
//	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<UserDTO> create(@ModelAttribute @Valid UserDTO userDTO) throws IOException {
		if (userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {

			final String UPLOAD_FOLDER = "C:/Users/Admin/Documents/hackerthon/hackerthon-Image/";
			String filename = userDTO.getFile().getOriginalFilename();
			File newFile = new File(UPLOAD_FOLDER + filename);

			userDTO.getFile().transferTo(newFile);
			userDTO.setAvatar(filename);
		}
		// goi qua Service
		
		userService.create(userDTO);
		statisticService.create( new StatisticDTO("Account "+ userDTO.getName()+ " is created", new Date()));
		// Noti
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setFrom("nguyenmanhdung01122000@gmail.com");
		messageDTO.setTo("nguyenmanhdung01122001@gmail.com");
		messageDTO.setSubject("Account "+ userDTO.getName()+ " is created");
		messageDTO.setContent("Account "+ userDTO.getName()+ " is created");
		messageDTO.setToName(userDTO.getName());
		notificationService.sendNotification(messageDTO);
		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userDTO).build();
	}

	@GetMapping("/me")
//	@PreAuthorize("isAuthenticated()")
	public ResponseDTO<UserDTO> me(Principal principal) {
		String username = principal.getName();

		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value()))
				.data(userService.getUserByUsername(username)).build();
	}

	@PutMapping("/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<UserDTO> update(@ModelAttribute @Valid UserDTO userDTO) throws IOException {
		userService.update(userDTO);
//		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userDTO).build();

	}

	@GetMapping("/{id}")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<UserDTO> get(@PathVariable(value = "id") int id) {
		return ResponseDTO.<UserDTO>builder().code(String.valueOf(HttpStatus.OK.value())).data(userService.get(id))
				.build();
	}

	@DeleteMapping("/{id}")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<Void> delete(@PathVariable(value = "id") int id) {

		userService.delete(id);
		statisticService.create( new StatisticDTO("Account have id "+ id+" is delete", new Date()));
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@DeleteMapping("/delete/all/{ids}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<Void> deleteAll(@PathVariable(value = "ids") List<Integer> ids) {
		userService.deleteAll(ids);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

	@PostMapping("/search")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseDTO<List<UserDTO>> search(@RequestBody @Valid SearchDTO searchDTO) {
		logger.info("AccountService Controller: get all accounts");
		statisticService.create( new StatisticDTO("GET ALL ACOUNT ", new Date()));
		return userService.find(searchDTO);
	}

	@PutMapping("/update-password")
	public ResponseDTO<Void> updatePassword(@ModelAttribute @Valid UserDTO userDTO) throws IOException {
		userService.updatePassword(userDTO);
		return ResponseDTO.<Void>builder().code(String.valueOf(HttpStatus.OK.value())).build();
	}

}
