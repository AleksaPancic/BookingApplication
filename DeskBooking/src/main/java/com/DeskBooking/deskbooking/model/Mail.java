package com.DeskBooking.deskbooking.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
	private String nameTo;
	private String from;
	private String mailTo;
	
	private List<Object> attachments;
	private Map<String, Object> props;
}
