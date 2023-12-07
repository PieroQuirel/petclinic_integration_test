package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialist;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.exception.SpecialistNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j

public class SpecialistServiceTest {

    @Autowired
    private SpecialistService specialistService;


    @Test
    public void testFindSpecialistById() {

        Integer ID = 1;
        String NAME = "radiology";
        Specialist specialist = null;

        try {

            specialist = this.specialistService.findById(ID);

        } catch (SpecialistNotFoundException e) {
            fail(e.getMessage());
        }
        log.info("" + specialist);

        assertEquals(NAME, specialist.getName());

    }
    @Test
    public void testCreateSpecialist() {

        String SPEC_NAME = "Alessandro";
        String SPEC_OFFICE = "Quiroz";
        Integer SPEC_H_OPEN = 7;
        Integer SPEC_H_CLOSE = 14;

        Specialist specialist = new Specialist(SPEC_NAME, SPEC_OFFICE, SPEC_H_OPEN, SPEC_H_CLOSE);

        Specialist specialistCreated = this.specialistService.create(specialist);

        log.info("SPECIALIST CREATED :" + specialistCreated.toString());

        assertNotNull(specialistCreated.getId());
        assertEquals(SPEC_NAME, specialistCreated.getName());
        assertEquals(SPEC_OFFICE, specialistCreated.getOffice());
        assertEquals(SPEC_H_OPEN, specialistCreated.getH_open());
        assertEquals(SPEC_H_CLOSE, specialistCreated.getH_close());

    }

    @Test
    public void testUpdatedSpecialist() {
        String SPEC_NAME = "Alessandro";
        String SPEC_OFFICE = "Quiroz";
        Integer SPEC_H_OPEN = 7;
        Integer SPEC_H_CLOSE = 14;

        String UP_SPEC_NAME = "Harold";
        String UP_SPEC_OFFICE = "Lima";
        Integer UP_SPEC_H_OPEN = 6;
        Integer UP_SPEC_H_CLOSE = 12;

        Specialist specialist = new Specialist(SPEC_NAME, SPEC_OFFICE, SPEC_H_OPEN, SPEC_H_CLOSE);

        log.info(">" + specialist);
        Specialist specialistCreated = this.specialistService.create(specialist);
        log.info(">>" + specialistCreated);

        specialistCreated.setName(UP_SPEC_NAME);
        specialistCreated.setOffice(UP_SPEC_OFFICE);
        specialistCreated.setH_open(UP_SPEC_H_OPEN);
        specialistCreated.setH_close(UP_SPEC_H_CLOSE);

        Specialist upgradeSpecialist = this.specialistService.update(specialistCreated);
        log.info(">>>" + upgradeSpecialist);

        assertEquals(UP_SPEC_NAME, upgradeSpecialist.getName());
        assertEquals(UP_SPEC_OFFICE, upgradeSpecialist.getOffice());
        assertEquals(UP_SPEC_H_OPEN, upgradeSpecialist.getH_open());
        assertEquals(UP_SPEC_H_CLOSE, upgradeSpecialist.getH_close());
    }



    @Test
    public void testDeleteSpecialist () {

        String SPEC_NAME = "Alessandro";
        String SPEC_OFFICE = "";
        Integer SPEC_H_OPEN = 7;
        Integer SPEC_H_CLOSE = 14;

        // ------------ Create ---------------

        Specialist  specialist = new Specialist (SPEC_NAME, SPEC_OFFICE, SPEC_H_OPEN, SPEC_H_CLOSE);
        specialist = this.specialistService.create(specialist);
        log.info("" + specialist);

        // ------------ Delete ---------------

        try {
            this.specialistService.delete(specialist.getId());
        } catch (SpecialistNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.specialistService.findById(specialist.getId());
            assertTrue(false);
        } catch (SpecialistNotFoundException e) {
            assertTrue(true);
        }

    }

    // Carlitos Torres: Implementando método para encontrar por ID



}