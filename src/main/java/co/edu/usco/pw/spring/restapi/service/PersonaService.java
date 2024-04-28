package co.edu.usco.pw.spring.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.usco.pw.spring.restapi.model.Persona;

@Service
public class PersonaService {

	static List<Persona> personas = new ArrayList<Persona>();
	static long id = 0;

	public List<Persona> findAll() {
		return personas;
	}

	public List<Persona> findByTitleContaining(String name) {
		return personas.stream().filter(persona -> persona.getName().contains(name)).toList();
	}

	public Persona findById(long id) {
		return personas.stream().filter(persona -> id == persona.getId()).findAny().orElse(null);
	}

	public Persona save(Persona persona) {
		// update persona
		if (persona.getId() != 0) {
			long _id = persona.getId();

			for (int idx = 0; idx < personas.size(); idx++)
				if (_id == personas.get(idx).getId()) {
					personas.set(idx, persona);
					break;
				}

			return persona;
		}

		// create new persona
		persona.setId(++id);
		personas.add(persona);
		return persona;
	}

	public void deleteById(long id) {
		personas.removeIf(persona -> id == persona.getId());
	}

	public void deleteAll() {
		personas.removeAll(personas);
	}

}
