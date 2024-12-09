package tn.esprit.eventsproject.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EventServiceSpringTest {

    @Autowired
    private IEventServices eventServices;

    @Test
    void testAddParticipant() {
        Participant participant = new Participant();
        participant.setNom("Test");
        participant.setPrenom("User");

        Participant savedParticipant = eventServices.addParticipant(participant);

        assertNotNull(savedParticipant.getIdPart());
        assertEquals("Test", savedParticipant.getNom());
    }

    @Test
    void testAddAffectEvenParticipant() {
        // Create and save a participant first
        Participant participant = new Participant();
        participant.setNom("Test");
        participant.setPrenom("User");
        Participant savedParticipant = eventServices.addParticipant(participant);

        // Create event and affect participant
        Event event = new Event();
        event.setDescription("Test Event");

        Event savedEvent = eventServices.addAffectEvenParticipant(event, savedParticipant.getIdPart());

        assertNotNull(savedEvent.getIdEvent());
    }



    @Test
    void testGetLogisticsDates() {
        LocalDate dateDebut = LocalDate.now();
        LocalDate dateFin = LocalDate.now().plusDays(7);

        List<Logistics> logisticsList = eventServices.getLogisticsDates(dateDebut, dateFin);

        // The test result will depend on your database content
        assertNotNull(logisticsList);
    }
}