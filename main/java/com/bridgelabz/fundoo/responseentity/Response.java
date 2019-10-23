package com.bridgelabz.fundoo.responseentity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response 
{
	int statusCode;
	Object data;
	String message;
}
