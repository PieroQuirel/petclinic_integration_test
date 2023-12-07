package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.SpecialistTO;
import com.tecsup.petclinic.entities.Specialist;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.exception.SpecialistNotFoundException;
import com.tecsup.petclinic.mapper.SpecialistMapper;
import com.tecsup.petclinic.services.SpecialistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class SpecialistController {

    //@Autowired
    private SpecialistService specialistService;

    //@Autowired
    private SpecialistMapper mapper;

    /**
     *  Change
     * @param specialistService
     * @param mapper
     */
    public SpecialistController(SpecialistService specialistService, SpecialistMapper mapper){
        this.specialistService = specialistService;
        this.mapper = mapper ;
    }

    /**
     * Get all pets
     *
     * @return
     */
    @GetMapping(value = "/specialists")
    public ResponseEntity<List<SpecialistTO>> findAllSpecialists() {

        List<Specialist> specialists = (List<Specialist>) specialistService.findAll();
        log.info("specialists: " + specialists);
        specialists.forEach(item -> log.info("Specialist >>  {} ", item));

        List<SpecialistTO> specialistsTO = this.mapper.toSpecialistList(specialists);
        log.info("specialistsTO: " + specialistsTO);
        specialistsTO.forEach(item -> log.info("SpecialistTO >>  {} ", item));

        return ResponseEntity.ok(specialistsTO);

    }


    /**
     * Create pet
     *
     * @param specialistTO
     * @return
     */
    @PostMapping(value = "/specialists")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<SpecialistTO> create(@RequestBody SpecialistTO specialistTO) {

        Specialist newSpecialist = this.mapper.toSpecialist(specialistTO);
        SpecialistTO newSpecialistTO = this.mapper.toSpecialistTO(specialistService.create(newSpecialist));

        return  ResponseEntity.status(HttpStatus.CREATED).body(newSpecialistTO);

    }


    /**
     * Find pet by id
     *
     * @param id
     * @return
     * @throws PetNotFoundException
     */
    @GetMapping(value = "/specialists/{id}")
    ResponseEntity<SpecialistTO> findById(@PathVariable Integer id) {

        SpecialistTO specialistTO = null;

        try {
            Specialist specialist = specialistService.findById(id);
            specialistTO = this.mapper.toSpecialistTO(specialist);

        } catch (SpecialistNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specialistTO);

    }

    /**
     * Update and create specialist
     *
     * @param specialistTO
     * @param id
     * @return
     */
    @PutMapping(value = "/specialist/{id}")
    ResponseEntity<SpecialistTO>  update(@RequestBody SpecialistTO specialistTO, @PathVariable Integer id) {

        SpecialistTO updateSpecialistTO= null;

        try {

            Specialist updateSpecialist = specialistService.findById(id);

            /* REVISAR DESPUES */
            updateSpecialist.setName(specialistTO.getName());
            updateSpecialist.setOffice(specialistTO.getOffice());
            updateSpecialist.setH_open(specialistTO.getH_open());
            updateSpecialist.setH_close(specialistTO.getH_close());


            specialistService.update(updateSpecialist);

            updateSpecialistTO = this.mapper.toSpecialistTO(updateSpecialist);

        } catch (SpecialistNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updateSpecialistTO);
    }

    /**
     * Delete specialist by id
     *
     * @param id
     */
    @DeleteMapping(value = "/specialist/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {

        try {
            specialistService.delete(id);
            return ResponseEntity.ok(" Delete ID :" + id);
        } catch (SpecialistNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
