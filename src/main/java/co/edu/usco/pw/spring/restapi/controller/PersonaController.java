package co.edu.usco.pw.spring.restapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.pw.spring.restapi.model.Persona;
import co.edu.usco.pw.spring.restapi.service.PersonaService;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PersonaController {
	@Autowired
	PersonaService personaService;

	@GetMapping("/personas")
	public ResponseEntity<List<Persona>> getAllPersonas(@RequestParam(required = false) String name) {
		try {
			List<Persona> personas = new ArrayList<Persona>();

			if (name == null)
				personaService.findAll().forEach(personas::add);
			else
				personaService.findByTitleContaining(name).forEach(personas::add);

			if (personas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(personas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/personas/{id}")
	public ResponseEntity<Persona> getPersonasById(@PathVariable("id") long id) {
		Persona persona = personaService.findById(id);

		if (persona != null) {
			return new ResponseEntity<>(persona, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/personas")
	public ResponseEntity<Persona> createPersonas(@RequestBody Persona persona) {
		try {
			Persona p = personaService.save(persona);
			return new ResponseEntity<>(p, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/personas/{id}")
	public ResponseEntity<Persona> updatePersonas(@PathVariable("id") long id, @RequestBody Persona persona) {
		Persona p = personaService.findById(id);

		if (p != null) {
			p.setName(persona.getName());
			p.setLastname(persona.getLastname());
			return new ResponseEntity<>(personaService.save(p), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/personas/{id}")
	public ResponseEntity<HttpStatus> deletePersonas(@PathVariable("id") long id) {
		try {
			personaService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/personas")
	public ResponseEntity<HttpStatus> deleteAllPersonas() {
		try {
			personaService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
