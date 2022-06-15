package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Indirizzo;
import com.example.demo.repository.IndirizzoRepository;

@Service
public class IndirizzoService {
	@Autowired
	private IndirizzoRepository indirizzoRepository;
	
	@Transactional
	public void save(Indirizzo inidirizzo) {
		this.indirizzoRepository.save(inidirizzo);
	}
	
	@Transactional
	public void remove(Long id) {
		indirizzoRepository.deleteById(id);
	}
	
	public Indirizzo findById(Long id) {
		return indirizzoRepository.findById(id).get();
	}
	
//	public boolean alreadyExists(Trainer trainer) {
//		return this.buffetRepository.existsByNomeAndDescrizione(buffet.getNome(), buffet.getDescrizione());
//	}
	
	public List<Indirizzo> findAll(){
		List<Indirizzo> indirizzi = new ArrayList<Indirizzo>();
		for(Indirizzo i : indirizzoRepository.findAll()) {
			indirizzi.add(i);
		}
		return indirizzi;
	}
}
