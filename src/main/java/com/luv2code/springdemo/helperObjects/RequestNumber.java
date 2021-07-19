package com.luv2code.springdemo.helperObjects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.luv2code.springdemo.customAnnotation.RequestNumberExists;

//Helper Object for checking if entered number in input field of a form for desired Request. Used on /requestFormAdmin
public class RequestNumber {

	@NotNull(message="{requestNumber.number.notNull}")
	//typeMismatch.requestNumber=Input should be a number
	@NumberFormat(style = Style.NUMBER) 
	@Min(value = 1, message="{requestNumber.number.min}")
	@RequestNumberExists
	private Integer number;	
	
		
	public RequestNumber() {
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
