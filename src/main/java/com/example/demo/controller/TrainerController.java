package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Trainer;
import com.example.demo.service.TrainerService;
import com.example.demo.validator.TrainerValidator;

@Controller
public class TrainerController {
	
	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private TrainerValidator trainerValidator;
	
	

	@GetMapping("/admin/trainers")
	public String getTrainers(Model model) {
		List<Trainer> trainers = trainerService.findAll();  
		model.addAttribute("trainers", trainers);
		return "trainer/trainers.html";
	}

	@GetMapping("/admin/trainer/{id}")
	public String getTrainerById(@PathVariable("id") Long id, Model model) {
		Trainer trainer = trainerService.findById(id);
		model.addAttribute("trainer", trainer);
		return "trainer/trainer.html";
	}

	@GetMapping("/admin/trainerForm")
	public String getTrainer(Model model) {
		model.addAttribute("trainer", new Trainer());
		return "trainer/trainerForm.html";
	}

	@PostMapping("/admin/trainer")
	public String addTrainer(@Valid @ModelAttribute("trainer") Trainer trainer, BindingResult bindingResults, Model model) {
		this.trainerValidator.validate(trainer, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			trainerService.save(trainer);
			model.addAttribute("trainers", trainerService.findAll());
			return "trainer/trainers.html";
		}
		return "trainer/trainerForm.html";
	}	
	

	@PostMapping("/admin/cancellaTrainer/{id}")
	public String removeTrainer(@PathVariable("id") Long id, Model model) {
		this.trainerService.remove(id);
		model.addAttribute("trainers", this.trainerService.findAll());
		return "trainer/trainers.html";
	}
	
	@GetMapping("/admin/modificaTrainer/{id}")
	public String getModificaTrainer(@PathVariable("id") Long id, Model model) {
		Trainer trainer = this.trainerService.findById(id);
		model.addAttribute("trainer", trainer);
		return "trainer/modificaTrainer.html";
	}
	
	@PostMapping("/admin/trainer/modifica/{id}")
	public String updateTrainer(@Valid @ModelAttribute("trainer") Trainer trainer, @PathVariable("id") Long id, BindingResult bindingResults, Model model) {
		this.trainerValidator.validate(trainer, bindingResults);
		Trainer oldTrainer = this.trainerService.findById(id);
		
		if(!bindingResults.hasErrors()) {
			oldTrainer.setNome(trainer.getNome());
			oldTrainer.setCognome(trainer.getCognome());
			oldTrainer.setNazionalita(trainer.getNazionalita());
			oldTrainer.setEta(trainer.getEta());
			
			trainerService.save(oldTrainer);
			model.addAttribute("trainers", trainerService.findAll());
			return "trainer/trainers.html";
		}
		model.addAttribute("trainer", oldTrainer);
		return "trainer/modificaTrainer.html";
	}
	
	
	
	
}
