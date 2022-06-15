package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.service.IndirizzoService;

@Controller
public class IndirizzoController {

	@Autowired
	private IndirizzoService indirizzoService;
}
