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

import com.example.demo.model.Sala;
import com.example.demo.service.SalaService;
import com.example.demo.validator.SalaValidator;

@Controller
public class SalaController {

	@Autowired
	private SalaService salaService;
	
	@Autowired
	private SalaValidator salaValidator;
	
	/* TODO */
	@GetMapping("/admin/sale")
	public String getSale(Model model) {
		List<Sala> sale = salaService.findAll();  
		model.addAttribute("sale", sale);
		return "admin/sala/sale.html";
	}
	
	@GetMapping("/admin/sala/{id}")
	public String getSalaById(@PathVariable("id") Long id, Model model) {
		Sala sala = salaService.findById(id);
		model.addAttribute("sala", sala);
		return "admin/sala/sala.html";
	}

	@GetMapping("/admin/salaForm")
	public String getSala(Model model) {
		model.addAttribute("sala", new Sala());
		return "admin/sala/salaForm.html";
	}

	@PostMapping("/admin/sala")
	public String addSala(@Valid @ModelAttribute("sala") Sala sala, BindingResult bindingResults, Model model) {
		this.salaValidator.validate(sala, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			salaService.save(sala);
			return "redirect:/admin/sale";
		}
		return "admin/sala/salaForm.html";
	}	
	

	@PostMapping("/admin/cancellaSala/{id}")
	public String removeSala(@PathVariable("id") Long id, Model model) {
		this.salaService.removeSalaDaCorsi(id);
		this.salaService.remove(id);
		return "redirect:/admin/sale";
	}
	
//	@GetMapping("/admin/modificaTrainer/{id}")
//	public String getModificaTrainer(@PathVariable("id") Long id, Model model) {
//		Trainer trainer = this.trainerService.findById(id);
//		model.addAttribute("trainer", trainer);
//		return "admin/trainer/modificaTrainer.html";
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
//			return "admin/trainer/trainers.html";
//		}
//		model.addAttribute("trainer", oldTrainer);
//		return "admin/trainer/modificaTrainer.html";
//	}
}
