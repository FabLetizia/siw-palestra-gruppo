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

import com.example.demo.model.Corso;
import com.example.demo.model.Trainer;
import com.example.demo.service.CorsoService;
import com.example.demo.service.SalaService;
import com.example.demo.service.TrainerService;
import com.example.demo.validator.CorsoValidator;

@Controller
public class CorsoController {
	@Autowired
	private CorsoService corsoService;
	
	@Autowired
	private CorsoValidator corsoValidator;
	
	@Autowired
	private SalaService salaService;
	
	@Autowired
	private TrainerService trainerService;
	
	@GetMapping("/admin/corsi")
	public String getCorsi(Model model) {
		List<Corso> corsi = corsoService.findAll();  
		model.addAttribute("corsi", corsi);
		return "corso/corsi.html";
	}

	@GetMapping("/admin/corso/{id}")
	public String getCorsoById(@PathVariable("id") Long id, Model model) {
		Corso corso = corsoService.findById(id);
		model.addAttribute("corso", corso);
		model.addAttribute("trainer", corso.getTrainer());
		model.addAttribute("sala", corso.getSala());
		return "corso/corso.html";
	}

	@GetMapping("/admin/corsoForm")
	public String getCorso(Model model) {
		model.addAttribute("corso", new Corso());
		model.addAttribute("trainers", trainerService.findAll());
		model.addAttribute("sale", salaService.findAll());
		return "corso/corsoForm.html";
	}

	@PostMapping("/admin/corso")
	public String addCorso(@Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResults, Model model) {
		this.corsoValidator.validate(corso, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			corsoService.save(corso);
			model.addAttribute("corsi", corsoService.findAll());
			return "corso/corsi.html";
		}
		model.addAttribute("trainers", trainerService.findAll());
		model.addAttribute("sale", salaService.findAll());
		return "corso/corsoForm.html";
	}	
	

	@PostMapping("/admin/cancellaCorso/{id}")
	public String removeCorso(@PathVariable("id") Long id, Model model) {
		this.corsoService.remove(id);
		model.addAttribute("corsi", this.corsoService.findAll());
		return "corso/corsi.html";
	}
	
//	@GetMapping("/admin/modificaTrainer/{id}")
//	public String getModificaTrainer(@PathVariable("id") Long id, Model model) {
//		Trainer trainer = this.trainerService.findById(id);
//		model.addAttribute("trainer", trainer);
//		return "trainer/modificaTrainer.html";
//	}
//	
//	@PostMapping("/admin/trainer/modifica/{id}")
//	public String updateTrainer(@Valid @ModelAttribute("trainer") Trainer trainer, @PathVariable("id") Long id, BindingResult bindingResults, Model model) {
//		this.trainerValidator.validate(trainer, bindingResults);
//		Trainer oldTrainer = this.trainerService.findById(id);
//		
//		if(!bindingResults.hasErrors()) {
//			oldTrainer.setNome(trainer.getNome());
//			oldTrainer.setCognome(trainer.getCognome());
//			oldTrainer.setNazionalita(trainer.getNazionalita());
//			oldTrainer.setEta(trainer.getEta());
//			
//			trainerService.save(oldTrainer);
//			model.addAttribute("trainers", trainerService.findAll());
//			return "trainer/trainers.html";
//		}
//		model.addAttribute("trainer", oldTrainer);
//		return "trainer/modificaTrainer.html";
//	}
}
