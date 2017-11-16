/**
 * 
 */
package com.cisco.spring.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cisco.spring.mongodb.document.Employee;

/**
 * EmployeeRepositpry Interface created to manage and handle all Employee Document persisted (CRUD) operations.
 * 
 * @author mkatnam
 *
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	
	/*As of now we are not declaring any new methods in Interface and availing the existing methods from MongoRepository.
	If we want we can declare new methods here and will implement in implementation class for future usage */

}
