package com.app.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class WarehouseApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(WarehouseApplicationTests.class);

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Disabled
	public void testSaveUom() throws Exception {

		log.info("Inside testSaveUom():");

		// a. Create one Http Request using Mock
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/rest/api/uom/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"uomType\":\"PACKING\",\"uomModel\": \"APPLE PHONE\", \"uomDesc\": \"SAMPLE ONE A\"}");

		// b. Execute Request and Get Result using mockMvc (Environment)
		MvcResult result = mockMvc.perform(request).andReturn();

		// c. Read Response from Result
		MockHttpServletResponse response = result.getResponse();

		// d. Validate/Assert Response using JUnit API
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		if (!response.getContentAsString().contains("Created")) {
			fail("UOM is not Created!");
		}
	}

	@Test
	@Disabled
	public void testUomAll() throws Exception {
		log.info("Inside testUomAll():");

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/rest/api/uom/findAll");

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
	}

	@Test
	@Disabled
	public void testGetOneUom() throws Exception {
		log.info("Inside testGetOneUom():");

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/rest/api/uom/find/{id}", 302);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
	}

	@Test
	@Disabled
	public void testUomUpdate() throws Exception {

		log.info("Inside testUomUpdate():");

		// a. Create one Http Request using Mock
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/rest/api/uom/update")
				.contentType(MediaType.APPLICATION_JSON).content(
						"{\"id\": 302,\"uomType\":\"PACKING\",\"uomModel\": \"LENOVO PHONE\", \"uomDesc\": \"SAMPLE TEST\"}");
		// b. Execute Request and Get Result using mockMvc (Environment)
		MvcResult result = mockMvc.perform(request).andReturn();

		// c. Read Response from Result
		MockHttpServletResponse response = result.getResponse();

		// d. Validate/Assert Response using JUnit API
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		if (!response.getContentAsString().contains("Updated")) {
			fail("UOM Update is Fail!");
		}
	}

	@Test
	public void testdeleteUom() throws Exception {

		log.info("Inside testdeleteUom():");

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/rest/api/uom/delete/{id}", 202);

		MvcResult result = mockMvc.perform(request).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		if (!response.getContentAsString().contains("Deleted")) {
			fail("Uom is not Deleted:");
		}
	}
}
