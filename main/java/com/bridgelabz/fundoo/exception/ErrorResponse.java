package com.bridgelabz.fundoo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse 
{
	private int statusCode;
	private Object data;
	private String message;
	
}
