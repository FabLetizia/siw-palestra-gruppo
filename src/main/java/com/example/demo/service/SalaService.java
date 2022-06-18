package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Corso;
import com.example.demo.model.Sala;
import com.example.demo.repository.SalaRepository;

@Service
public class SalaService {
	
	@Autowired
	private SalaRepository salaRepository;
	
	@Transactional
	public void save(Sala sala) {
		this.salaRepository.save(sala);
	}
	
	@Transactional
	public void remove(Long id) {
		salaRepository.deleteById(id);
	}
	
	public Sala findById(Long id) {
		return salaRepository.findById(id).get();
	}
	
	public boolean alreadyExists(Sala sala) {
		return this.salaRepository.existsByNome(sala.getNome());
	}
	
	public List<Sala> findAll(){
		List<Sala> sale = new ArrayList<>();
		for(Sala s : salaRepository.findAll()) {
			sale.add(s);
		}
		return sale;
	}

	public void removeSalaDaCorsi(Long id) {
		Sala sala = salaRepository.findById(id).get();
		for(Corso c: sala.getCorsi()) {
			c.setSala(null);
		}
	}

	public void updateSala(Sala sala) {
		this.save(sala);	
	}
	
}
