/**
 * 
 */

package com.cisco.spring.mongodb.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.cisco.spring.SpringMongoDbRestApplication;
import com.cisco.spring.mongodb.document.Employee;
import com.cisco.spring.mongodb.repository.EmployeeRepository;

/**
 * @author mkatnam
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringMongoDbRestApplication.class)
@WebAppConfiguration
public class EmployeeControllerTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
	
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
	
	List<Employee> employees = new ArrayList<Employee>();
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        employees = this.employeeRepository.findAll();
        System.out.println(" Employees Size in setUp() :"+employees.size());

    }


	/**
	 * Test method for {@link com.cisco.spring.mongodb.controller.EmployeeController#listAllEmployees()}.
	 * @throws Exception 
	 */
	@Test
	public void testListAllEmployees() throws Exception {
		mockMvc.perform(get("/rest/employees"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$", hasSize(8)));
	}

	/**
	 * Test method for {@link com.cisco.spring.mongodb.controller.EmployeeController#getEmployeesById(java.lang.String)}.
	 * @throws Exception 
	 */
	
	 @Test
	    public void testGetEmployeesById() throws Exception {
	        mockMvc.perform(get("/rest/employees/1003"))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType(contentType))
	                .andExpect(jsonPath("$", hasSize(1)))
	                .andExpect(jsonPath("$[0].empId", is("1003")))
	                .andExpect(jsonPath("$[0].empName", is("Mallesham")))
	                .andExpect(jsonPath("$[0].designation", is("Lead Developer")))
	                .andExpect(jsonPath("$[0].company", is("TechM")))
	                .andExpect(jsonPath("$[0].location", is("IND")))
	                .andExpect(jsonPath("$[0].employmentType", is("Vendor")));
	    }

}
