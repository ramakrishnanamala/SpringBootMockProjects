package com.example.GitHubEvents.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.GitHubEvents.entity.Events;
import com.example.GitHubEvents.service.EventsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class EventsControllerTests {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	EventsService eventsService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void givenEventObject_whenCreateEvent_thenReturnSavedEvent() throws Exception {
		Events events = new Events();
		
			events.setId(1);
			events.setType("PushEvent");
			events.setRepoId(1);
			events.setActorId(1);
			events.setPublic(true);
			
			given(eventsService.createEvent(any(Events.class)))
						.willAnswer((invocation)-> invocation.getArgument(0));
			
			ResultActions response = mockMvc.perform(post("/events")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(events)));
			
			response.andDo(print()).
			andExpect(jsonPath("$.id", is(events.getId())))
			.andExpect(jsonPath("$.type", is(events.getType())))
			.andExpect(jsonPath("$.repoId", is(events.getRepoId())))
			.andExpect(jsonPath("$.actorId", is(events.getActorId())))
			.andExpect(jsonPath("$.Public", is(events.getPublic())));
			
	}
	
	@Test
	public void givenEvent_whenGetEvents_thenReturnEvents() throws Exception {
		Events events1 = new Events();
		
		events1.setId(1);
		events1.setType("PushEvent");
		events1.setRepoId(1);
		events1.setActorId(1);
		events1.setPublic(true);
		
		given(eventsService.getAllEvents()).willReturn(List.of(events1));
		
		ResultActions response = mockMvc.perform(get("/events"));
		
		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.events1", is(events1)));
	}
	
	@Test
	public void givenEventId_whenGetEventId_thenReturnEventObject() throws Exception {
		int id = 1;
		
		Events events2 = new Events();
		
		events2.setType("PushEvent");
		events2.setRepoId(1);
		events2.setActorId(1);
		events2.setPublic(true);
		
		given(eventsService.findByEventId(id)).willReturn(Optional.of(events2));
		
		ResultActions response = mockMvc.perform(get("/events/{id}", id));
		
		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.type", is(events2.getType())))
			.andExpect(jsonPath("$.repoId", is(events2.getRepoId())))
			.andExpect(jsonPath("$.actorId", is(events2.getActorId())))
			.andExpect(jsonPath("$.Public", is(events2.getPublic())));
			
		
	}
	
	@Test
	public void givenRepoId_whenGetEvents_thenReturnEventsObject() throws Exception {
		
		int repoId = 1;
		
		Events events3 = new Events();
		
		events3.setId(1);
		events3.setType("PushEvent");
		events3.setActorId(1);
		events3.setPublic(true);
		
		given(eventsService.findByEventId(repoId)).willReturn(Optional.of(events3));
		
		ResultActions response = mockMvc.perform(get("/repos/{repoId}/events", repoId));
		
		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.id", is(events3.getId())))
			.andExpect(jsonPath("$.type", is(events3.getType())))
			.andExpect(jsonPath("$.actorId", is(events3.getActorId())))
			.andExpect(jsonPath("$.Public", is(events3.getPublic())));
	}
}
