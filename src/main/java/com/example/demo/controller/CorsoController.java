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
		return "admin/corso/corsi.html";
	}

	@GetMapping("/admin/corso/{id}")
	public String getCorsoById(@PathVariable("id") Long id, Model model) {
		Corso corso = corsoService.findById(id);
		model.addAttribute("corso", corso);
		String nomeSala = "ancora nessuna sala";
		if(corsoService.hasSalaNull(corso))
			model.addAttribute("sala", nomeSala);
		else
			model.addAttribute("sala", corso.getSala().getNome());
		
		return "admin/corso/corso.html";
	}

	@GetMapping("/admin/corsoForm")
	public String getCorso(Model model) {
		model.addAttribute("corso", new Corso());
		model.addAttribute("trainers", trainerService.findAll());
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/corsoForm.html";
	}

	@PostMapping("/admin/corso")
	public String addCorso(@Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResults, Model model) {
		this.corsoValidator.validate(corso, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			corsoService.save(corso);
			return "redirect:/admin/corsi";
		}
		model.addAttribute("trainers", trainerService.findAll());
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/corsoForm.html";
	}	
	

	@PostMapping("/admin/cancellaCorso/{id}")
	public String removeCorso(@PathVariable("id") Long id, Model model) {
		this.corsoService.remove(id);
		return "redirect:/admin/corsi";
	}
	
	@PostMapping("/admin/cancellaCorsoDaTrainer/{id1}/{id2}")
	public String removeCorsoDaTrainer(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2, Model model) {
		this.corsoService.remove(id1);
		return "redirect:/admin/trainer/"+id2;
	}
	
	@GetMapping("/admin/corsoFormPerTrainer/{id}")
	public String getCorsoFormPerTrainer(@PathVariable("id") Long id, Model model) {
		model.addAttribute("corso", new Corso(trainerService.findById(id)));
		model.addAttribute("trainer", trainerService.findById(id));
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/corsoFormPerTrainer.html";
	}
	
	@PostMapping("/admin/addCorsoPerTrainer/{id}")
	public String addCorsoPerTrainer(@PathVariable("id") Long id, @Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResults, Model model) {
		this.corsoValidator.validate(corso, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			corsoService.save(corso);
			return "redirect:/admin/trainer/"+id;
		}
		model.addAttribute("trainer", trainerService.findById(id));
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/corsoFormPerTrainer.html";
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
