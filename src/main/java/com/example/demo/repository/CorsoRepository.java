package com.example.demo.repository;

import java.time.LocalTime;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Corso;

public interface CorsoRepository extends CrudRepository<Corso, Long> {
	public boolean existsByNomeAndGiornoAndOra(String nome, String giorno, LocalTime ora);

}
