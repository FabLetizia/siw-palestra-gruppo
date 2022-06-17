package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Sala;
import com.example.demo.service.SalaService;

@Controller
public class SalaController {

	@Autowired
	private SalaService salaService;
	
	/* TODO */
	@GetMapping("/admin/sale")
	public String getSale(Model model) {
		List<Sala> sale = salaService.findAll();  
		model.addAttribute("sale", sale);
		return "admin/sala/sale.html";
	}
}
