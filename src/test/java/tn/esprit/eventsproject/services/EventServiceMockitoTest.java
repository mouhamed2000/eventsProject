package tn.esprit.eventsproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.entities.Tache;
import tn.esprit.eventsproject.repositories.EventRepository;
import tn.esprit.eventsproject.repositories.LogisticsRepository;
import tn.esprit.eventsproject.repositories.ParticipantRepository;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EventServiceMockitoTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private LogisticsRepository logisticsRepository;

    @InjectMocks
    private EventServicesImpl eventServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddParticipant() {
        Participant participant = new Participant();
        participant.setIdPart(1);
        participant.setNom("Test");

        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        Participant result = eventServices.addParticipant(participant);

        verify(participantRepository, times(1)).save(participant);
        assertEquals(participant.getIdPart(), result.getIdPart());
    }

    @Test
    void testAddAffectEvenParticipant() {
        Event event = new Event();
        Participant participant = new Participant();
        participant.setIdPart(1);

        when(participantRepository.findById(1)).thenReturn(Optional.of(participant));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventServices.addAffectEvenParticipant(event, 1);

        verify(eventRepository, times(1)).save(event);
        assertNotNull(result);
    }

    @Test
    void testGetLogisticsDates() {
        LocalDate dateDebut = LocalDate.now();
        LocalDate dateFin = LocalDate.now().plusDays(7);

        List<Event> events = new ArrayList<>();
        Event event = new Event();
        Set<Logistics> logisticsSet = new HashSet<>();
        Logistics logistics = new Logistics();
        logistics.setReserve(true);
        logisticsSet.add(logistics);
        event.setLogistics(logisticsSet);
        events.add(event);

        when(eventRepository.findByDateDebutBetween(dateDebut, dateFin)).thenReturn(events);

        List<Logistics> result = eventServices.getLogisticsDates(dateDebut, dateFin);

        verify(eventRepository, times(1)).findByDateDebutBetween(dateDebut, dateFin);
        assertFalse(result.isEmpty());
    }

    @Test
    void testCalculCout() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.setDescription("Test Event");
        Set<Logistics> logisticsSet = new HashSet<>();
        Logistics logistics = new Logistics();
        logistics.setReserve(true);
        logistics.setPrixUnit(100);
        logistics.setQuantite(2);
        logisticsSet.add(logistics);
        event.setLogistics(logisticsSet);
        events.add(event);

        when(eventRepository.findByParticipants_NomAndParticipants_PrenomAndParticipants_Tache(
                "Tounsi", "Ahmed", Tache.ORGANISATEUR))
                .thenReturn(events);

        eventServices.calculCout();

        verify(eventRepository, times(1)).save(any(Event.class));
    }
}