package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.service.TrainerService;

@Controller
public class TrainerController {
	@Autowired
	private TrainerService trainerService;
}
