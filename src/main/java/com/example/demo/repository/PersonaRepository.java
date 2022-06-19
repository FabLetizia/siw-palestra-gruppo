package com.example.demo.repository;

import java.time.LocalTime;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Corso;
import com.example.demo.model.Persona;

public interface PersonaRepository extends CrudRepository<Persona, Long> {
	public boolean existsByEmailAndCorso(String email, Corso corso);

}
