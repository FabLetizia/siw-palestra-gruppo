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
	
	@GetMapping("/admin/personaForm/{id}")
	public String getPersonaForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("persona", new Persona());
		model.addAttribute("corso", corsoService.findById(id));
		return "admin/corso/personaForm.html";
	}
	
	@PostMapping("/admin/addPersonaACorso/{id}")
	public String addPersonaPerCorso(@PathVariable("id") Long id, @Valid @ModelAttribute("persona") Persona persona, BindingResult bindingResults, Model model) {
		Corso corso = corsoService.findById(id);
		Persona p = persona;
		if((personaService.personaGiàInserita(persona))) {
			p = personaService.findByEmail(persona.getEmail());
		}
				
		if(this.personaService.corsoEsistentePerPersona(p, corso)){
			this.personaValidator.validate(p, bindingResults);
		}
		
		if(!bindingResults.hasErrors()) {
			p.getCorsi().add(corso);
			corso.decrementaNumeroPosti();
			corsoService.save(corso);
			personaService.save(p);
			
			return "redirect:/admin/corso/"+id;
		}
		model.addAttribute("corso", this.corsoService.findById(id));		
		return "admin/corso/personaForm.html";
	}
}
