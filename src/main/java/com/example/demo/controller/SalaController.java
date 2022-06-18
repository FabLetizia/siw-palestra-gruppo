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
	
	@GetMapping("/admin/modificaSala/{id}")
	public String getModificaSala(@PathVariable("id") Long id, Model model) {
		Sala sala = this.salaService.findById(id);
		model.addAttribute("sala", sala);
		return "admin/sala/modificaSala.html";
	}
	
	@PostMapping("/admin/updateSala/{id}")
	public String updateSala(@Valid @ModelAttribute("sala") Sala sala, @PathVariable("id") Long id, BindingResult bindingResults, Model model) {
		Sala oldSala = this.salaService.findById(id);
		
		if(!bindingResults.hasErrors()) {
			oldSala.setNome(sala.getNome());
			oldSala.setCapienza(sala.getCapienza());
			
			salaService.updateSala(oldSala);
			model.addAttribute("sala", oldSala);
			return "redirect:/admin/sale";
		}
		else 
			return "admin/sala/modificaSala.html";
	}

}
