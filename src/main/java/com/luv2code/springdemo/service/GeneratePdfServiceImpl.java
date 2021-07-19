package com.luv2code.springdemo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.XRRuntimeException;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

@Service
public class GeneratePdfServiceImpl implements GeneratePdfService {

	private static final Logger logger = LoggerFactory.getLogger(GeneratePdfServiceImpl.class);
	
	@Override
	public byte[] dataPdf(String html) throws SAXException, ParserConfigurationException, XRRuntimeException {
		
		// loading font for cyrilic support, glyph icons and bootstrap css
		final ClassPathResource usedFont = new ClassPathResource("./for_pdf_generation/open-sans.ttf");
		final ClassPathResource glyphIcons = new ClassPathResource("./for_pdf_generation/glyphicons-halflings-regular.ttf");
		final ClassPathResource css = new ClassPathResource("./for_pdf_generation/bootstrap.min.css");
		
		// Creating the Flying Saucer renderer
		ITextRenderer renderer = new ITextRenderer(50f + 10f/3f, 40);
 
		// Retrieving the font resolver and setting the custom fonts
		ITextFontResolver resolver = renderer.getFontResolver();
		
		try {			
			resolver.addFont (usedFont.getURL().toString(), BaseFont.IDENTITY_H, true);			
		} 
		catch (DocumentException | IOException e) {			
			logger.warn("PDF error, Text Font not found. ");
		}
		
		try {			
			resolver.addFont (glyphIcons.getURL().toString(), BaseFont.IDENTITY_H, true);			
		}
		catch (DocumentException | IOException e) {			
			logger.warn("PDF error, Glyph Font not found. ");
		}
		   
//		DO NOT DELETE !!!
//		Set sb = ITextFontResolver.getDistinctFontFamilyNames(usedFont.getURL().toString(), BaseFont.IDENTITY_H,true);		   
//		System.out.println(sb.toArray()[0]);
//	         		   
//		Set glyph = ITextFontResolver.getDistinctFontFamilyNames(glyphIcons.getURL().toString(), BaseFont.IDENTITY_H,true);		   
//		System.out.println(glyph.toArray()[0]);

		// Has No Effect
//		renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
	         
//		renderer.setDocument(doc, null);
		
		// Setting the document for Flying Saucer and using the bootstrap css from the resources classpath
		try {
			renderer.setDocumentFromString(html, css.getURL().toExternalForm());
		} 
		catch (NullPointerException | IOException e) {
			logger.warn("PDF error, CSS not found. ");
		} 
		finally{
			// If css is not found, proceed without css
			if (css.exists() == false){
				renderer.setDocumentFromString(html);
			}
		}
		
		renderer.layout();
		   
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		// Writing the pdf to the output streem
		renderer.createPDF(bos);
 
		// byte data for pdf
		byte[] dataPdf = bos.toByteArray();
		
//		Closing a ByteArrayOutputStream has no effect. The methods in this class can be called after the stream has been closed without generating an IOException.		
//		bos.close();
		
		return dataPdf;
	}

}





