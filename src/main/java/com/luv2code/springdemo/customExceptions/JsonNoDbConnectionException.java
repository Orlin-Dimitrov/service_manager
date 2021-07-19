package com.luv2code.springdemo.customExceptions;

//Used For JSON Exception Handling
public class JsonNoDbConnectionException extends Exception {

	/**
	 *  Custom exception when generating JSON and no connection to Database
	 */
	private static final long serialVersionUID = -781059747578596634L;

	public JsonNoDbConnectionException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JsonNoDbConnectionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
