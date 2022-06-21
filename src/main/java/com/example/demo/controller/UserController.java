package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Corso;
import com.example.demo.model.Persona;
import com.example.demo.model.Trainer;
import com.example.demo.service.CorsoService;
import com.example.demo.service.TrainerService;



@Controller
public class UserController {

	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private CorsoService corsoService;
	
	@GetMapping("/user/trainers")
	public String getTrainers(Model model) {
		List<Trainer> trainers = trainerService.findAll();  
		model.addAttribute("trainers", trainers);
		return "user/userTrainers.html";
	}
	
	@GetMapping("/user/corsi")
	public String getCorsi(Model model) {
		List<Corso> corsi = corsoService.findAll();
		model.addAttribute("corsi", corsi);
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/trainer/{id}")
	public String getTrainerById(@PathVariable("id") Long id, Model model) {
		Trainer trainer = trainerService.findById(id);
		model.addAttribute("trainer", trainer);
		return "user/userTrainer.html";
	}
	
//	@GetMapping("/user/corsi/{giorno}")
//	public String getCorsiPerGiorno(@PathVariable("giorno") String giorno, Model model){
//		model.addAttribute("corsi", this.corsoService.findByGiorno(giorno));
//		return "user/userCorsi.html";
//	}
	
	@GetMapping("/user/corsilun")
	public String getCorsiPerLunedi( Model model){
		model.addAttribute("corsi", this.corsoService.findByGiorno("Lunedi"));
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/corso/{id}")
	public String getCorso(@PathVariable("id") Long id, Model model) {
		Corso corso = corsoService.findById(id);
		model.addAttribute("corso", corso);
		String nomeSala = "ancora nessuna sala";
		if(corsoService.hasSalaNull(corso))
			model.addAttribute("sala", nomeSala);
		else
			model.addAttribute("sala", corso.getSala().getNome());
		return "user/userCorso.html";
	}
}
