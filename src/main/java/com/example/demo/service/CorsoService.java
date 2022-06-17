package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Corso;
import com.example.demo.repository.CorsoRepository;

@Service
public class CorsoService {
	
	@Autowired
	private CorsoRepository corsoRepository;
	
	@Transactional
	public void save(Corso corso) {
		this.corsoRepository.save(corso);
	}
	
	@Transactional
	public void remove(Long id) {
		corsoRepository.deleteById(id);
	}
	
	public Corso findById(Long id) {
		return corsoRepository.findById(id).get();
	}
	
	public boolean alreadyExists(Corso corso) {
		return this.corsoRepository.existsByNomeAndGiornoAndOraInizio(corso.getNome(), corso.getGiorno(),
				corso.getOraInizio());
	}
	
	public List<Corso> findAll(){
		List<Corso> corsi = new ArrayList<>();
		for(Corso c : corsoRepository.findAll()) {
			corsi.add(c);
		}
		return corsi;
	}

	public boolean hasSalaNull(Corso corso) {
		return corso.getSala() == null;
	}
}
