/**
 * 
 */
package com.cisco.spring.mongodb.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cisco.alfresco.repo.rest.utils.AlfrescoUtils;
import com.cisco.spring.mongodb.document.Employee;
import com.cisco.spring.mongodb.repository.EmployeeRepository;

/**
 * EmployeeController class will handle all Employee REST calls (CRUD) for CRUD operations.
 * 
 * @author mkatnam
 *
 */

@RestController
@RequestMapping("/rest")
public class EmployeeController {
	private static Logger logger = Logger.getLogger(EmployeeController.class);
	
	//EmployeeRepository service for EMployee document persistance
	private EmployeeRepository employeeRepository;

	//constructor to initialize the employeeRepository bean from spring boot.
	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	/**
	 * Get All employees available for Employee Document in DB
	 * @return
	 */
	@GetMapping(value = "/employees")
	public List<Employee> listAllEmployees(){
		if(logger.isInfoEnabled()){
			logger.info("EmployeeController invoked by REST call and listAllEmployees() is executing..... ");
		}
		List<Employee> list = employeeRepository.findAll();
		
		 return employeeRepository.findAll();
	}
	
	/** Get EMployee details for specific empID
	 * @param empId
	 * @return
	 */
	@GetMapping(value = "/employees/{empId}")
	public String getEmployeesById(@PathVariable("empId") String id){
		StringBuilder sb =  null;
		if(logger.isInfoEnabled()){
			logger.info("EmployeeController invoked by REST call and getEmployeesById() is executing..... ");
			logger.info(" Emp ID === >>"+id);
		}
		
		Employee empObj = employeeRepository.findOne(id);
		if(empObj != null){
			sb = new StringBuilder("<table border=\"1\" style=\"font-size:13px\"><tr><th>Emp ID</th><th>Emp Name</th><th>Designation</th><th>Company</th><th>Location</th</tr><tr><td>");
			sb.append(empObj.getEmpId()).append("</td><td>").append(empObj.getEmpName()).append("</td><td>").append(empObj.getDesignation()).append("</td><td>").append(empObj.getCompany()).append("</td><td>").append(empObj.getLocation()).append("</td></tr></table>");
		}
		
		 return sb.toString();
	}
	
	/** Get EMployee details for specific empID
	 * @param empId
	 * @return
	 */
	@GetMapping(value = "/test/employees")
	public String getTestEmployees(){
		String id = "1003";
		StringBuilder sb =  null;
		if(logger.isInfoEnabled()){
			logger.info("EmployeeController invoked by REST call and getEmployeesById() is executing..... ");
			logger.info(" Emp ID === >>"+id);
		}
		Employee empObj = employeeRepository.findOne(id);
		if(empObj != null){
			sb = new StringBuilder("<table border=\"1\" style=\"font-size:13px\"><tr><th>Emp ID</th><th>Emp Name</th><th>Designation</th><th>Company</th><th>Location</th</tr><tr><td>");
			sb.append(empObj.getEmpId()).append("</td><td>").append(empObj.getEmpName()).append("</td><td>").append(empObj.getDesignation()).append("</td><td>").append(empObj.getCompany()).append("</td><td>").append(empObj.getLocation()).append("</td></tr></table>");
		}
		
		 return sb.toString();
	}
	
	/** Get EMployee details for specific empID
	 * @param empId
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@GetMapping(value = "/getRecordInfo")
	public String getRecordInfo() throws IOException, ParseException{
		System.out.println("get Record info called.......");
		String ticketURLResponse = AlfrescoUtils.getAlfTicket();
		String nodeRef = "b512c418-170c-4719-8868-ea3b2aac0c23";
		JSONObject json = AlfrescoUtils.getRecordInfoJSON(ticketURLResponse,nodeRef);
		
		StringBuilder sb = new StringBuilder("<table border=\"1\" style=\"font-size:13px\"><tr><th>Disposition Autority</th><th>Disposition Date</th></tr><tr><td>");
		sb.append(json.get("identifier")).append("</td><td>").append(json.get("dispositionDate")).append("</td></tr></table>");
		
		 return sb.toString();
	}
	
	@GetMapping(value = "/getRecordInfo/{nodeId}")
	public String getRecordInfoByPathVar(@PathVariable("nodeId") String nodeRef) throws IOException, ParseException{
		System.out.println("get Record info called.......");
		System.out.println("nodeRef ---->> "+nodeRef);
		String ticketURLResponse = AlfrescoUtils.getAlfTicket();
		String json = AlfrescoUtils.getRecordInfo(ticketURLResponse,nodeRef);
		
		/*StringBuilder sb = new StringBuilder("<font size=\"2\"><table border=\"1\"><tr><th>Disposition Autority</th><th>Disposition Date</th></tr><tr><td>");
		sb.append(json.get("identifier")).append("</td><td>").append(json.get("dispositionDate")).append("</td></tr></table></font>");*/
		
		 return json;
	}
	
	@GetMapping(value = "/getRecordInfo/json/{nodeId}")
	public String getRecordInfoByString(@PathVariable("nodeId") String nodeRef) throws IOException, ParseException{
		System.out.println("get Record info called.......");
		System.out.println("nodeRef ---->> "+nodeRef);
		String ticketURLResponse = AlfrescoUtils.getAlfTicket();
		JSONObject json = AlfrescoUtils.getRecordInfoJSON(ticketURLResponse,nodeRef);
		
		StringBuilder sb = new StringBuilder("<table border=\"1\" style=\"font-size:13px\"><tr><th>Disposition Autority</th><th>Disposition Date</th></tr><tr><td>");
		sb.append(json.get("identifier")).append("</td><td>").append(json.get("dispositionDate")).append("</td></tr></table>");
		
		 return sb.toString();
	}
	
	@GetMapping(value = "/getVersionHistory/{nodeId}")
	public String getVersionHistory(@PathVariable("nodeId") String nodeRef) throws IOException, ParseException{
		System.out.println("getVersionHistory called.......");
		System.out.println(" nodeRef ---->> "+nodeRef);
		String ticketURLResponse = AlfrescoUtils.getAlfTicket();
		JSONObject json = AlfrescoUtils.getVersionHistory(ticketURLResponse,nodeRef);
		StringBuilder sb = new StringBuilder("<table border=\"1\" style=\"font-size:13px\"><tr><th>Version Node</th><th>EDCS ID</th><th>Creation Date</th><th>Deletion Date</th></tr><tr><td>");
		sb.append(json.get("nodeRef")).append("</td><td>").append(json.get("docnum")).append("</td><td>").append(json.get("createdDate")).append("</td><td>").append(json.get("deletionDateUI")).append("</td></tr></table>");
		
		 return sb.toString();
	}

	@GetMapping(value = "/errorcodes/webservice/{code}")
	public String getErrorCodesforWebServices(@PathVariable("code") String code) throws IOException, ParseException{
		System.out.println("getErrorCodesforWebServices called.......");
		System.out.println("code ---->> "+code);
		Map<String,String> errorCodes = new HashMap<String,String>();
		errorCodes.put("521", "Invalid folder Path. (May be thrown during the index building)");
		errorCodes.put("522", "Not a valid NodeRef.");
		errorCodes.put("523", "Invalid metadata.");
		errorCodes.put("524", "Invalid input format.");
		errorCodes.put("525", "You don't have permission to perform this action.");
		errorCodes.put("526", "Authentication Failed.");
		errorCodes.put("527", "Document already checked out.");
		errorCodes.put("528", "Document not checked out.");
		errorCodes.put("529", "Conflict in operation.");
		errorCodes.put("530", "Operation failed.");
		errorCodes.put("531", "Noderef cannot be null or blank.");
		errorCodes.put("532", "Folder not empty.");
		errorCodes.put("533", "Invalid permission set.");
		errorCodes.put("534", "Invalid user name.");
		errorCodes.put("535", "Migrated document is not yet ready to use.");
		
		
		 return errorCodes.get(code);
	}
	
	@GetMapping(value = "/serverStatus/dev")
	public static String devServerStatus() throws IOException{
		String strURL = "http://alfwsx-app-dev-01:38080/";
		return getServerStatus(strURL);
	}
	
	@GetMapping(value = "/serverStatus/dev2")
	public static String dev2ServerStatus() throws IOException{
		StringBuilder alive = new StringBuilder();
		String strURL = "http://alfcms-app-dev-02:8080/";
		String strAuditURL = "http://alfcms-app-dev-02:18080/";
		
		alive.append(getServerStatus(strURL)).append("<br /> ").append(getServerStatus(strAuditURL));
		//alive.append(getServerStatus(strURL)).append(System.lineSeparator()).append(getServerStatus(strAuditURL));
		System.out.println(" Status : "+alive.toString());
		return alive.toString();
	}
	
	public static String getServerStatus(String strURL) throws IOException{
		String alive = "up";
		URL url = new URL(strURL);
		HttpURLConnection httpConn =  (HttpURLConnection)url.openConnection();
		httpConn.setInstanceFollowRedirects( false );
		httpConn.setRequestMethod("HEAD"); 
		try{
		    httpConn.connect();
		     System.out.println( strURL +" Server is up  : " + httpConn.getResponseCode());
		}catch(java.net.ConnectException e){
		     System.out.println( strURL +"Server is down ");
		     alive = "down";
		}
		return strURL+" is "+alive;
	}
	
}
