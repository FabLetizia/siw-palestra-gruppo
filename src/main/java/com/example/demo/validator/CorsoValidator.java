package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Corso;
import com.example.demo.service.CorsoService;

@Component
public class CorsoValidator implements Validator {

	@Autowired
	private CorsoService cs;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Corso.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.cs.alreadyExists((Corso) target)) {
			errors.reject("corso.duplicato");
		}
		
		if(this.cs.SalaOccupata((Corso) target)) {
			errors.reject("corso.insalaoccupata");
		}
		
		if(this.cs.hasNullTrainer((Corso) target)) {
			errors.reject("corso.trainernull");
		}
		
	}
	
	public void validateDellaSala(Object target, Errors errors) {
		if(this.cs.SalaOccupata((Corso) target)) {
			errors.reject("corso.insalaoccupata");
		}
		
	}
}
