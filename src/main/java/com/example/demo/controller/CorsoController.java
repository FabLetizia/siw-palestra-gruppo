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
			corso.setNumeroPosti(corso.getSala().getCapienza());
			corsoService.save(corso);
			return "redirect:/admin/corsi";
		}
		model.addAttribute("trainers", trainerService.findAll());
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/corsoForm.html";
	}	
	

	@PostMapping("/admin/cancellaCorso/{id}")
	public String removeCorso(@PathVariable("id") Long id, Model model) {
		this.corsoService.removeCorsoDaPersone(corsoService.findById(id));
		this.corsoService.remove(id);
		return "redirect:/admin/corsi";
	}
	
	@PostMapping("/admin/cancellaCorsoDaTrainer/{id1}/{id2}")
	public String removeCorsoDaTrainer(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2, Model model) {
		this.corsoService.removeCorsoDaPersone(corsoService.findById(id1));
		this.corsoService.remove(id1);
		return "redirect:/admin/trainer/"+id2;
	}
	
	@GetMapping("/admin/corsoFormPerTrainer/{id}")
	public String getCorsoFormPerTrainer(@PathVariable("id") Long id, Model model) {
		model.addAttribute("corso", new Corso());
		model.addAttribute("trainer", trainerService.findById(id));
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/corsoFormPerTrainer.html";
	}
	
	@PostMapping("/admin/addCorsoPerTrainer/{id}")
	public String addCorsoPerTrainer(@PathVariable("id") Long id, @Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResults, Model model) {
		corso.setTrainer(trainerService.findById(id));
		this.corsoValidator.validate(corso, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			corso.setNumeroPosti(corso.getSala().getCapienza());
			corsoService.save(corso);
			return "redirect:/admin/trainer/"+id;
		}
		model.addAttribute("trainer", trainerService.findById(id));
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/corsoFormPerTrainer.html";
	}	
	
	@GetMapping("/admin/corsoFormPerSala/{id}")
	public String getCorsoFormPerSala(@PathVariable("id") Long id, Model model) {
		model.addAttribute("corso", new Corso());
		model.addAttribute("sala", salaService.findById(id));
		model.addAttribute("trainers", trainerService.findAll());
		return "admin/corso/corsoFormPerSala.html";
	}
	
	@PostMapping("/admin/addCorsoPerSala/{id}")
	public String addCorsoPerSala(@PathVariable("id") Long id, @Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResults, Model model) {
		corso.setSala(salaService.findById(id));
		this.corsoValidator.validate(corso, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			corso.setNumeroPosti(corso.getSala().getCapienza());
			corsoService.save(corso);
			return "redirect:/admin/sala/"+id;
		}
		model.addAttribute("sala", salaService.findById(id));
		model.addAttribute("trainers", trainerService.findAll());
		return "admin/corso/corsoFormPerSala.html";
	}
	
	@PostMapping("/admin/cancellaCorsoDaSala/{id1}/{id2}")
	  public String removeCorsoDaSala(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2, Model model) {
		  salaService.findById(id1).getCorsi().remove(corsoService.findById(id2));
		  corsoService.findById(id2).setSala(null);
		  salaService.updateSala(salaService.findById(id1));
		  corsoService.findById(id2).setNumeroPosti(0);
		  corsoService.updateCorso(corsoService.findById(id2));
		  return "redirect:/admin/sala/"+id1;
	  }
	
	@GetMapping("/admin/modificaCorso/{id}")
	public String getModificaCorso(@PathVariable("id") Long id, Model model) {
		Corso corso = this.corsoService.findById(id);
		model.addAttribute("corso", corso);
		model.addAttribute("sale", salaService.findAll());
		return "admin/corso/modificaCorso.html";
	}
	
	@PostMapping("/admin/aggiornaCorso/{id}")
	public String updateCorso(@PathVariable Long id, @Valid @ModelAttribute("corso") Corso corso, BindingResult bindingResults, Model model) {
		this.corsoValidator.validateDellaSala(corso, bindingResults);
		if(!bindingResults.hasErrors()) {
			Corso oldCorso = corsoService.findById(id);
			oldCorso.setId(corso.getId());
			oldCorso.setNome(corso.getNome());
			oldCorso.setDescrizione(corso.getDescrizione());
			oldCorso.setGiorno(corso.getGiorno());
			oldCorso.setOraInizio(corso.getOraInizio());
			oldCorso.setOraFine(corso.getOraFine());
			oldCorso.setSala(corso.getSala());
			oldCorso.setNumeroPosti(corso.getSala().getCapienza()-oldCorso.getPersone().size());
			corsoService.updateCorso(oldCorso);
			model.addAttribute("corso", oldCorso);
			return "redirect:/admin/corsi";
		}
		else {
			model.addAttribute("corso", corso);
			model.addAttribute("sale", salaService.findAll());
			return "admin/corso/modificaCorso.html";
		}
			
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
