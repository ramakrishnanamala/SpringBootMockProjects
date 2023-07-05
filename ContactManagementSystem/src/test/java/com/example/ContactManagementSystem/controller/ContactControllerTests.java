package com.example.ContactManagementSystem.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.ContactManagementSystem.entity.Person;
import com.example.ContactManagementSystem.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ContactControllerTests {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	ContactService contactService;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void givenPersonObject_whenCreatePerson_thenReturnSavedPerson() throws Exception {
		Person person = new Person();
		person.setId(1);
		person.setName("Foo Bar");
		person.setMobile("987465238");

		given(contactService.createPerson(any(Person.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));
		
		ResultActions response = mockMvc.perform(post("contact/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(person)));
		
		response.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", is(person.getId())))
			.andExpect(jsonPath("$.name", is(person.getName())))
			.andExpect(jsonPath("$.mobile", is(person.getMobile())));
	}
	
	@Test
	public void givenContactId_whenGetContactId_thenReturnContactObject() throws Exception {
		Integer id = 1;
		Person person1 = new Person();
		person1.setName("Foo Bar");
		person1.setMobile("987465238");
		
		given(contactService.getPersonById(id)).willReturn(person1);
		
		ResultActions response = mockMvc.perform(get("contact/retrieve/{id}", id));
		
		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name", is(person1.getName())))
				.andExpect(jsonPath("$.mobile", is(person1.getMobile())));
	}
}
