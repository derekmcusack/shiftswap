package com.chinaglia.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chinaglia.model.ShiftSwap;
import com.chinaglia.repository.ShiftSwapRepository;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;

@EnableEmailTools
@Service("shiftSwapService")
public class ShiftSwapServiceImpl implements ShiftSwapService{

	@Autowired 
    private MyMailService mailService;
	@Autowired
	private ShiftSwapRepository shiftSwapRepository;	
	
	@Override
	public void saveShiftSwap(ShiftSwap shiftSwap){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		shiftSwap.setEmail(email);
		shiftSwapRepository.save(shiftSwap);
		try {
			mailService.sendEmail();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}