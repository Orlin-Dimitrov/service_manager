package com.luv2code.springdemo.service;

import javax.xml.parsers.ParserConfigurationException;

import org.xhtmlrenderer.util.XRRuntimeException;
import org.xml.sax.SAXException;

public interface GeneratePdfService {
	
	// Generate PDF file for given Request
	public byte[] dataPdf (String html) throws SAXException, ParserConfigurationException, XRRuntimeException;
	
}
