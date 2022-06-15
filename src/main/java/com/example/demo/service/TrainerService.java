package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Trainer;
import com.example.demo.repository.TrainerRepository;

public class TrainerService {
	@Autowired
	private TrainerRepository trainerRepository;
	
	@Transactional
	public void save(Trainer trainer) {
		this.trainerRepository.save(trainer);
	}
	
	@Transactional
	public void remove(Long id) {
		trainerRepository.deleteById(id);
	}
	
	public Trainer findById(Long id) {
		return trainerRepository.findById(id).get();
	}
	
//	public boolean alreadyExists(Trainer trainer) {
//		return this.buffetRepository.existsByNomeAndDescrizione(buffet.getNome(), buffet.getDescrizione());
//	}
	
	public List<Trainer> findAll(){
		List<Trainer> trainers= new ArrayList<Trainer>();
		for(Trainer t : trainerRepository.findAll()) {
			trainers.add(t);
		}
		return trainers;
	}
}
