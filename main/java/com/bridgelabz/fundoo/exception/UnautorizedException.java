package com.bridgelabz.fundoo.exception;

public class UnautorizedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UnautorizedException(String message) 
	{
		super(message);
	}
}
