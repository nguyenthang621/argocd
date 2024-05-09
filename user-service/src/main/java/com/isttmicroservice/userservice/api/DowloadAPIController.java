package com.isttmicroservice.userservice.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download")
public class DowloadAPIController {
	
	@GetMapping(value = "/{filename}", produces = "image/jpg")
	public ResponseEntity download(@PathVariable("filename") String filename, HttpServletResponse response)
			throws IOException {
		final String UPLOAD_FOLDER = "C:/uploadFile/";
		File file = new File(UPLOAD_FOLDER + filename);
		Files.copy(file.toPath(), response.getOutputStream());
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS)).build();
	}

}
