package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Corso;
import com.example.demo.model.Persona;
import com.example.demo.model.Trainer;
import com.example.demo.repository.PersonaRepository;

@Service
public class PersonaService {
	@Autowired
	private PersonaRepository personaRepository;
	
	@Transactional
	public void save(Persona persona) {
		this.personaRepository.save(persona);
	}
	
	@Transactional
	public void remove(Long id) {
		personaRepository.deleteById(id);
	}
	
	public Persona findById(Long id) {
		return personaRepository.findById(id).get();
	}
	
//	public boolean alreadyExists(Persona persona) {
//		return this.trainerRepository.existsByNomeAndCognomeAndNazionalitaAndEta(trainer.getNome(),
//				trainer.getCognome(), trainer.getNazionalita(),trainer.getEta());
//	}
	
//	public boolean alreadyExists(Persona persona) {
//		return this.personaRepository.existsByEmailAndCorso(persona.getEmail(), persona.getCorso());
//	}
	
	public List<Persona> findAll(){
		List<Persona> persone= new ArrayList<>();
		for(Persona p: personaRepository.findAll()) {
			persone.add(p);
		}
		return persone;
	}

	public boolean personaGiàInserita(Persona persona) {
		return this.personaRepository.existsByEmail(persona.getEmail());
		
	}

	public Persona findByEmail(String email) {
		return this.personaRepository.findByEmail(email);
	}
}
