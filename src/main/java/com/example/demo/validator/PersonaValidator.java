package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Persona;

@Component
public class PersonaValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Persona.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
			errors.reject("persona.esistente");
	}

}
