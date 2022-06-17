package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Sala;
import com.example.demo.service.SalaService;

@Component
public class SalaValidator implements Validator {

	@Autowired
	private SalaService ss;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Sala.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		if(this.ss.alreadyExists((Sala) o)) {
			errors.reject("sala.duplicato");
		}
	}

}
