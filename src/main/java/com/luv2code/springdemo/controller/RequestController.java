// ControlLer to Display List of Requests and Single Request Info Page

package com.luv2code.springdemo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xhtmlrenderer.util.XRRuntimeException;
import org.xml.sax.SAXException;

import com.luv2code.springdemo.generators.RequestInfoPage;
import com.luv2code.springdemo.helperObjects.DataTablesUniqueId;
import com.luv2code.springdemo.pdf.CharArrayWriterResponse;
import com.luv2code.springdemo.service.GeneratePdfService;
import com.luv2code.springdemo.service.RequestService;

@Controller
@RequestMapping("/requests")
public class RequestController {
	
	//injecting RequestInfoPage (with a method to generate request info page)
	@Autowired
	private RequestInfoPage requestInfoPage;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private GeneratePdfService generatePdfService;
	
	private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
	
	// Listing Requests with server side pagination
	@GetMapping("/list")
	public String listRequestsJSONPaginated(Model theModel) {
		
		// If the model doesn't contain listRequests model attribute, proceed.
		if (theModel.containsAttribute("listRequests") == false) {
			
			//Generating random UID for DataTables identifier for table state saving.
			UUID uuid = UUID.randomUUID();
			String dataTablesUniqueId = uuid.toString();
			
			//Creating new DataTablesUniqueId object to store the generated dataTablesUniqueId value. Necessary for Multi-Window use.
			DataTablesUniqueId listRequests = new DataTablesUniqueId();
			
			// Setting the generated dataTablesUniqueId value.
			listRequests.setDataTablesUniqueId(dataTablesUniqueId);
//			System.out.println("LIST REAUEST UUID: " + listRequests.getDataTablesUniqueId());
			
			// Adding the created object to the model
			theModel.addAttribute("listRequests", listRequests);
		}
		
		// Creating object listRequestsInfo to store the dataTablesUniqueId when visiting request info page.
		DataTablesUniqueId listRequestsInfo = new DataTablesUniqueId();
		
		// Adding the listRequestsInfo object to the model.
		theModel.addAttribute("listRequestsInfo", listRequestsInfo);
		
		return "list-requests";
	}
	
	
	// POST Method for Listing Requests with server side pagination, used when going Back from request info page.
	@PostMapping("/list")
	public String listRequestsJSONPaginatedPOST(@ModelAttribute("listRequests") DataTablesUniqueId listRequests,
												RedirectAttributes redirectAttributes,
												Model theModel) {
		
		// Adding Flash Attribute listRequests for PRG
		redirectAttributes.addFlashAttribute("listRequests", listRequests);
		
		return "redirect:/requests/list";
	}
	
	
	// POST Method for displaying Single Request info page(entered from list request page).
	@PostMapping("/{requestId}")
	public String showRequestPathVariablePOST(@PathVariable("requestId") int theId,
											@ModelAttribute("listRequestsInfo") DataTablesUniqueId listRequestsInfo,
											RedirectAttributes redirectAttributes,
											Model theModel) {
		
		// Adding Flash Attribute listRequestsInfo for PRG
		redirectAttributes.addFlashAttribute("listRequestsInfo", listRequestsInfo);
		
		return "redirect:/requests/" + theId;
	}	
	
	
	// GET Method for displaying Single Request info page.
	@GetMapping("/{requestId}")
	public String showRequestPathVariable(@PathVariable("requestId") int theId,	Model theModel) {
		
		// If the model contains listRequestsInfo model attribute, proceed. Else redirect to /requests/list page.
		if (theModel.containsAttribute("listRequestsInfo")) {
			
			// Creating ListRequests object which stores the necessary dataTablesUniqueId used when going back to /requests/list page(POST).
			DataTablesUniqueId listRequests = new DataTablesUniqueId();
			
			// Adding the listRequests object to the model.
			theModel.addAttribute("listRequests", listRequests);
			
			// Creating the info page
			requestInfoPage.infoPage(theId, theModel);
			
			return "request-info";
		}else {
			return "redirect:/requests/list";
		}
	}

	
	// PDF EXPORT
	// GET Method for displaying / generating  Single Request info page.
	@GetMapping("/{requestId}/notAccessablePageForPdfGeneration")
	public String exportRequestToPdf(@PathVariable("requestId") int theId,	Model theModel) {
		
		int lastRequest = requestService.getLastRequest().getId();
		
		if(theId > 0 && theId <= lastRequest) {
		
			// Creating the info page
			requestInfoPage.infoPage(theId, theModel);
			
			LocalDate today = LocalDate.now();
			
			LocalTime now = LocalTime.now();
			
			String currentDate =  today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			String currentTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			
			// model attribute with custom formated current date
			theModel.addAttribute("currentDate", currentDate);
			theModel.addAttribute("currentTime", currentTime);
			
			return "request-info-for-pdf";
		}else {
			return "pdf-error";
		}

	}
	
	
	// Get method to download PDF
	@GetMapping("/{requestId}/pdf")
	public ResponseEntity<byte[]> getPDF(@PathVariable("requestId") int theId, HttpServletRequest request , HttpServletResponse response, Model theModel) 
			throws  ServletException, IOException{

		// custom response write: writes the processed JSP to an HTML String
		// see : http://valotas.com/get-output-of-jsp-or-servlet-response/

		CharArrayWriterResponse customResponse = new CharArrayWriterResponse(response);
		request.getRequestDispatcher("/requests/"+theId+"/notAccessablePageForPdfGeneration").forward(request, customResponse);
		String html = customResponse.getOutput();
		
		byte[] dataPdf = null;
		
		// Try/catch block for Flying Saucer PDF generator
		try {
			dataPdf = generatePdfService.dataPdf(html);
			
		} catch (SAXException | ParserConfigurationException | XRRuntimeException e) {
			
			 logger.warn("Error generating PDF. Invalid XML");
		}
		   	    
		// Setting custom name from String adds time/date stamp to filename
//		String customFileName = "output.pdf";
		    
		ContentDisposition disposition = ContentDisposition.builder("attachment").filename("request_N_" + theId +".pdf").build();
		
		// Custom header, returning file as download option
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(disposition);
		headers.setContentType(MediaType.APPLICATION_PDF);		  
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		    
		ResponseEntity<byte[]> responsePdf = new ResponseEntity<>(dataPdf, headers, HttpStatus.OK);
		   
		return responsePdf;	
	}
	
}
	
/*
 
 // NOT USED , OPENHTMLTOPDF !!!!
 
 MAVEN DEPENDANCY
 
 		<dependency>
		    <groupId>com.openhtmltopdf</groupId>
		    <artifactId>openhtmltopdf-core</artifactId>
		    <version>1.0.1</version>
		</dependency>
		
		<dependency>
		    <groupId>com.openhtmltopdf</groupId>
		    <artifactId>openhtmltopdf-pdfbox</artifactId>
		    <version>1.0.1</version>
		</dependency>
		
		<dependency>
		    <groupId>org.jsoup</groupId>
		    <artifactId>jsoup</artifactId>
		    <version>1.12.1</version>
		</dependency>
	
	@RequestMapping(value="/{requestId}/getNewPdf", method=RequestMethod.GET)
	public ResponseEntity<byte[]> getNewPDF(@PathVariable("requestId") int theId, HttpServletRequest request , HttpServletResponse response) throws  Exception{
		

		// custom response write: writes the processed JSP to an HTML String
		// see : http://valotas.com/get-output-of-jsp-or-servlet-response/
		CharArrayWriterResponse customResponse = new CharArrayWriterResponse(response);
		request.getRequestDispatcher("/requests/"+theId+"/notAccessablePageForPdfGeneration").forward(request, customResponse);
		String html = customResponse.getOutput();
		
		System.out.println(">>> HTML file lenght: " + html.length());
		
//		byte[] data = html.getBytes();

		// send the generated HTML to XhtmlRenderer
//		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		Document doc = builder.parse(new ByteArrayInputStream(data));
		
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		final W3CDom w3cDom = new W3CDom();
        final Document w3cDoc = w3cDom.fromJsoup(Jsoup.parse(html));
		
		final PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();
		 pdfBuilder.useFastMode();
		 pdfBuilder.withW3cDocument(w3cDoc, "/");
		 pdfBuilder.toStream(bos);
		 pdfBuilder.run();

	   
		   byte[] dataPdf = bos.toByteArray();
		   
		   bos.close();
		   
		   HttpHeaders headers = new HttpHeaders();
		    
		    // Here you have to set the actual filename of your pdf
		    String filename = "output.pdf";
//		    headers.setContentDispositionFormData(filename, filename);
		    
		    ContentDisposition disposition = ContentDisposition.builder("attachment").filename(filename).build();
		    
		    headers.setContentDisposition(disposition);
		    headers.setContentType(MediaType.APPLICATION_PDF);
		  
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		    
		    ResponseEntity<byte[]> responsePdf = new ResponseEntity<>(dataPdf, headers, HttpStatus.OK);
		   
		 return responsePdf;
	}
*/
