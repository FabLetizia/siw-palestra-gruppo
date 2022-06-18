package com.example.demo.repository;

import java.time.LocalTime;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Corso;
import com.example.demo.model.Sala;

public interface CorsoRepository extends CrudRepository<Corso, Long> {
	
	public boolean existsByNomeAndGiornoAndOraInizio(String nome, String giorno, LocalTime oraInizio);

	public boolean existsByGiornoAndOraInizioAndSala(String giorno, LocalTime oraInizio, Sala sala);

}
