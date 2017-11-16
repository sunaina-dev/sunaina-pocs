/**
 * 
 */
package com.cisco.spring.mongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.cisco.spring.mongodb.document.Employee;
import com.cisco.spring.mongodb.repository.EmployeeRepository;

/**
 * MongoDB configuration class , it will find out required Document class for Repository 
 * and also read DB configuration information from application.properties to establish connection with mongoDB.
 * 
 * @author mkatnam
 *
 */

@EnableMongoRepositories(basePackageClasses = EmployeeRepository.class)
@Configuration
public class MongoDBConfig {

	
	/**
	 * Rather than inserting data into Employee Document from CommandLine, we are doing form here using CommandLineRunner interface. 
	 * But it is optional method for regular development. We can insert data by importing supported format documents from commands or
	 * from MongoDB client tools as well.
	 * 
	 * @param employeeRepository
	 * @return
	 */
	@Bean
	CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository){
		return new CommandLineRunner(){
			@Override
			public void run(String... arg0) throws Exception {
				System.out.println(" <<<<========= Inserting data into Employee table ===========>>>>>");
				employeeRepository.save(new Employee("1001","Narsim","Project Lead","ABC","USA","Contractor"));
				employeeRepository.save(new Employee("1002","Saroj","Program Manager","TechM","IND","Vendor"));
				employeeRepository.save(new Employee("1003","Mallesham","Lead Developer","TechM","IND","Vendor"));
				employeeRepository.save(new Employee("1004","Abhilasha","Project Lead","TechM","IND","Vendor"));
				employeeRepository.save(new Employee("1005","Mahesh","Senior Architect","TechM","HYD","Vendor"));
				employeeRepository.save(new Employee("1006","Swaroop","Developer","TechM","IND","Vendor"));
				employeeRepository.save(new Employee("1007","Jim","Program Manager","Cisco","USA","FTE"));
				employeeRepository.save(new Employee("1008","Mike","Senior Analyst","Cisco","USA","FTE"));
			}
		};
	}
}
