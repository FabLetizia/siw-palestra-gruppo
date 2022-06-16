package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Trainer;
import com.example.demo.service.TrainerService;

@Component
public class TrainerValidator implements Validator {

	@Autowired
	private TrainerService ts;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Trainer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.ts.alreadyExists((Trainer) target)) {
			errors.reject("trainer.duplicato");
		}
	}

}
