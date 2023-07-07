package com.example.GitHubEvents.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.GitHubEvents.entity.Events;
import com.example.GitHubEvents.repository.EventsRepository;

@ExtendWith(MockitoExtension.class)
public class EventsServiceTests {
	
	@Mock
	private EventsRepository eventsRepository;
	
	@InjectMocks
	EventsServiceImpl eventsServiceImpl;
	
	Events events;
	
	@BeforeEach
	public void setup() {
		Events events = new Events();
		
		events.setId(1);
		events.setType("PushEvent");
		events.setRepoId(1);
		events.setActorId(1);
		events.setPublic(true);
	}
	
	@DisplayName("JUnit test for create event method")
	@Test
	public void givenEventObject_whenCreateEvent_thenReturnEventObject() throws Exception {
		given(eventsRepository.save(events)).willReturn(events);
		
		System.out.println(eventsRepository);
		System.out.println(eventsServiceImpl);
		
		Events savedEvents = eventsServiceImpl.createEvent(events);
		
		System.out.println(savedEvents);
		
		assertThat(savedEvents).isNotNull();
	}
	
	@DisplayName("JUnit test for get Events method")
	@Test
	public void givenEvents_whenGetEvents_thenReturnEvents() throws Exception {
		
		Events events1 = new Events();
		events1.setId(2);
		events1.setType("ReleaseEvent");
		events1.setRepoId(1);
		events1.setActorId(1);
		events1.setPublic(true);
		
		given(eventsRepository.findAll()).willReturn(List.of(events,events1));
		
		List<Events> eventsList = eventsServiceImpl.getAllEvents();
		
		assertThat(eventsList).isNotNull();
		assertThat(eventsList.size()).isEqualTo(2);
		
	}
	
	@DisplayName("JUnit test for getEventById method")
    @Test
    public void givenEventId_whenGetEventById_thenReturnEventObject(){
        
        given(eventsRepository.findById(1)).willReturn(Optional.of(events));

        Events savedEvents = eventsServiceImpl.findByEventId(events.getId()).get();

        assertThat(savedEvents).isNotNull();

    }
	
	@DisplayName("JUnit test for getEventsByrepoId method")
    @Test
    public void givenRepoId_whenGetEventsByRepoId_thenReturnEventsObject() {
		
		Events events2 = new Events();
		events2.setId(2);
		events2.setType("ReleaseEvent");
		events2.setRepoId(1);
		events2.setActorId(1);
		events2.setPublic(true);
        
        given(eventsRepository.findByRepoId(1)).willReturn(List.of(events));

        Events savedEvents = eventsServiceImpl.findByEventId(events.getId()).get();

        assertThat(savedEvents).isNotNull();

    }
}
