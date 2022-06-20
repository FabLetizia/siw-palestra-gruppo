package com.example.demo.controller;

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
import com.example.demo.model.Persona;
import com.example.demo.service.CorsoService;
import com.example.demo.service.PersonaService;
import com.example.demo.validator.PersonaValidator;

@Controller
public class PersonaController {

	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private CorsoService corsoService;
	
	@Autowired
	private PersonaValidator personaValidator;
	
	@GetMapping("/user/personaForm/{id}") //id del corso
	public String getPersona(@PathVariable("id") Long id, Model model) {
		model.addAttribute("persona", new Persona());
		model.addAttribute("corso", corsoService.findById(id));
		return "user/personaForm.html";
	}
	
	@PostMapping("/user/addPersonaACorso/{id}")
	public String addPersonaPerCorso(@PathVariable("id") Long id, @Valid @ModelAttribute("persona") Persona persona, BindingResult bindingResults, Model model) {
		persona.setCorso(corsoService.findById(id));
		this.personaValidator.validate(persona, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			Corso corso = corsoService.findById(id);
			corso.decrementaNumeroPosti();
			corsoService.save(corso);
			personaService.save(persona);
			model.addAttribute("corso", corso);
			String nomeSala = "ancora nessuna sala";
			if(corsoService.hasSalaNull(corso))
				model.addAttribute("sala", nomeSala);
			else
				model.addAttribute("sala", corso.getSala().getNome());
			return "user/userCorso.html";
		}
		model.addAttribute("persona", new Persona());
		model.addAttribute("corso", corsoService.findById(id));
		return "user/personaForm.html";
	}
}
