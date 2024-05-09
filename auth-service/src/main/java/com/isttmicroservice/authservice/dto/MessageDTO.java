package com.isttmicroservice.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageDTO {
	@NonNull
	private String from;
	@NonNull
	private String to;
	private String toName;
	private String subject;
	private String content;
}
