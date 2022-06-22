package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Corso;
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
	
	@GetMapping("/user/corsi/lun")
	public String getCorsiPerLunedi( Model model){
		List<Corso> corsi = new ArrayList<>(this.corsoService.findByGiorno("Lunedi"));
		corsi.addAll(this.corsoService.findByGiorno("Lunedí"));
		corsi.addAll(this.corsoService.findByGiorno("Lunedì"));
		model.addAttribute("corsi", corsi);
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/corsi/mar")
	public String getCorsiPerMartedi( Model model){
		List<Corso> corsi = new ArrayList<>(this.corsoService.findByGiorno("Martedi"));
		corsi.addAll(this.corsoService.findByGiorno("Martedí"));
		corsi.addAll(this.corsoService.findByGiorno("Martedì"));
		model.addAttribute("corsi", corsi);
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/corsi/merc")
	public String getCorsiPerMercoledi( Model model){
		List<Corso> corsi = new ArrayList<>(this.corsoService.findByGiorno("Mercoledi"));
		corsi.addAll(this.corsoService.findByGiorno("Mercoledí"));
		corsi.addAll(this.corsoService.findByGiorno("Mercoledì"));
		model.addAttribute("corsi", corsi);
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/corsi/giov")
	public String getCorsiPerGiovedi( Model model){
		List<Corso> corsi = new ArrayList<>(this.corsoService.findByGiorno("Giovedi"));
		corsi.addAll(this.corsoService.findByGiorno("Giovedí"));
		corsi.addAll(this.corsoService.findByGiorno("Giovedì"));
		model.addAttribute("corsi", corsi );
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/corsi/ven")
	public String getCorsiPerVenerdi( Model model){
		List<Corso> corsi = new ArrayList<>(this.corsoService.findByGiorno("Venerdi"));
		corsi.addAll(this.corsoService.findByGiorno("Venerdí"));
		corsi.addAll(this.corsoService.findByGiorno("Venerdì"));
		model.addAttribute("corsi", corsi);
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/corsi/sab")
	public String getCorsiPerSabato( Model model){
		model.addAttribute("corsi", this.corsoService.findByGiorno("Sabato"));
		return "user/userCorsi.html";
	}
	
	@GetMapping("/user/corsi/dom")
	public String getCorsiPerDomenica( Model model){
		model.addAttribute("corsi", this.corsoService.findByGiorno("Domenica"));
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
