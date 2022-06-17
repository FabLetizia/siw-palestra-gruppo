package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Trainer;

public interface TrainerRepository extends CrudRepository<Trainer, Long> {
	
	public boolean existsByNomeAndCognomeAndNazionalitaAndEta(String nome, String cognome, String nazionalita,Integer eta);

}
