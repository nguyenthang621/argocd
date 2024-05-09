package com.isttmicroservice.notificationservice.dto;

import lombok.Data;

@Data
public class MessageDTO {
	 private String from;
	    private String to;
	    private String toName;
	    private String subject;
	    private String content;
}
