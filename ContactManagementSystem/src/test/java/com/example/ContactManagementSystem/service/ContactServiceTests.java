package com.example.ContactManagementSystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import com.example.ContactManagementSystem.entity.Person;
import com.example.ContactManagementSystem.repository.ContactRepository;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

	@Mock
	ContactRepository contactRepository;

	@InjectMocks
	ContactServiceImpl contactServiceImpl;

	Person person;

	@BeforeEach
	public void setup() {
		Person person = new Person();
		person.setId(1);
		person.setName("Foo Bar");
		person.setMobile("987465238");

	}

	@DisplayName("JUnit test for createContact method")
	@Test
	public void givenContactObject_whenSaveContact_thenReturnContactObject() {

		given(contactRepository.save(person)).willReturn(person);

		System.out.println(contactRepository);
		System.out.println(contactServiceImpl);

		Person savedPerson = contactServiceImpl.createPerson(person);

		System.out.println(savedPerson);

		assertThat(savedPerson).isNotNull();
	}

	@DisplayName("JUnit test for getContactById method")
	@Test
	public void givenContactId_whenGetContacyById_thenReturnContactObject() {

		given(contactRepository.findById(1)).willReturn(Optional.of(person));

		Person savedPerson = contactServiceImpl.getPersonById(person.getId());

		assertThat(savedPerson).isNotNull();
	}

}
