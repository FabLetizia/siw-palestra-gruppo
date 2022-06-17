package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Sala;

public interface SalaRepository extends CrudRepository<Sala, Long> {

	public boolean existsByNome(String nome);

}
