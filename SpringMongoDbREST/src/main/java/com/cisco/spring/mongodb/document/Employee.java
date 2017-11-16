/**
 * 
 */
package com.cisco.spring.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB document class to handle Employee document requests on DB.
 * 
 * @author mkatnam
 *
 */

@Document
public class Employee {
	
	@Id
	private String empId; // declaring empId as primary key in Employee Document.
    private String empName;
    private String designation;
    private String company;
    private String location;
    private String employmentType;
    
	public Employee(String empId, String empName, String designation, String company, String location,
			String employmentType) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.designation = designation;
		this.company = company;
		this.location = location;
		this.employmentType = employmentType;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
    
    

}
