package com.luv2code.springdemo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.springdemo.entity.RequestsPerDay;
import com.luv2code.springdemo.helperObjects.RequestsPerDayForGoogleCharts;
import com.luv2code.springdemo.helperObjects.StatisticsSelectYear;
import com.luv2code.springdemo.service.StatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;
	
	@GetMapping("/select")
	public String selectStatisticsYear(Model theModel) {
	
		StatisticsSelectYear selectedYear = new StatisticsSelectYear();
		theModel.addAttribute("selectedYear", selectedYear);

		LocalDate today = LocalDate.now();
		int currentYear = today.getYear();
		int startYear = 2016;
		
		List<Integer> listOfYears = new ArrayList<Integer>();
		
		for (int i = currentYear; i >= startYear; i--) {
			
			listOfYears.add(i);
		}
		
//		System.out.println("Years: " + listOfYears.toString());
		
		theModel.addAttribute("listOfYears", listOfYears);
		
		return "statistics-select";
	}
	
	@PostMapping("/data")
	public String showStatisticsDataPost(@ModelAttribute("selectedYear") StatisticsSelectYear selectedYear,
											Model theModel, RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("selectedYear", selectedYear);
		
		return "redirect:/statistics/data";
	}
	
	
	
	@GetMapping("/data")
	public String showStatisticsData(Model theModel) {
	
		if (theModel.containsAttribute("selectedYear")) {
			
			StatisticsSelectYear selectedYear =(StatisticsSelectYear)theModel.asMap().get("selectedYear");
			
			String yearForQuery = selectedYear.getYear();
			
//			System.out.println("Selected Year: " + yearForQuery);
			
			String diplayedYear = selectedYear.getYear();
			
			// Adding the selected year to the model. It is displayed on the page and used as JS variable.
			theModel.addAttribute("diplayedYear", diplayedYear);
			
			List<RequestsPerDay> totalRequestsPerDay = new ArrayList<RequestsPerDay>();
			
			//Retrieving from DB list of request per day for the specific year.
			totalRequestsPerDay = statisticsService.getRequestsPerDayForSpecificYear(yearForQuery);
			
			// Defining variable for max range for the GC graphic( JavaScript variable maxRange =  null -> relative; 0 and >0 -absolute)
			int maxRange = 0;
			
			//Creating list of requests per day with SPECIFIC format for proper chart drawing of Google Charts
			List<RequestsPerDayForGoogleCharts> totalRequestsPerDayForGC = new ArrayList<RequestsPerDayForGoogleCharts>();
			
			for (RequestsPerDay entry : totalRequestsPerDay) {
				
				RequestsPerDayForGoogleCharts tempRGC = new RequestsPerDayForGoogleCharts();
				
				tempRGC.setRequests(entry.getRequests());
				tempRGC.setYear(entry.getDate().getYear());
				
				//for javascript month must start from 0
				tempRGC.setMonth(entry.getDate().getMonthValue()-1);
				tempRGC.setDay(entry.getDate().getDayOfMonth());
				
//				System.out.println("Day: " + tempRGC.getDay() +  "; Month: " + tempRGC.getMonth() + "; Year: " + tempRGC.getYear() + "; Requests: " + tempRGC.getRequests());
								
				if(entry.getRequests() > maxRange) {
					maxRange = entry.getRequests();
				}
				
				totalRequestsPerDayForGC.add(tempRGC);				
			}
			
			//Model attribute for max range of Chart
			theModel.addAttribute("maxRange", maxRange);

			//Model attribute for Chart Data
			theModel.addAttribute("chartData", totalRequestsPerDayForGC);
			
			return "statistics-data";
		}else {
			return "redirect:/statistics/select";
		}
		
	}
	
}
