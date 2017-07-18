package com.chinaglia.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
	public void saveShiftSwap(ShiftSwap shiftSwap, String emailToSendTo){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		shiftSwap.setSwapEmail(email);
		shiftSwapRepository.save(shiftSwap);
		
		//send email notification - ***commented out for testing purposes***
//		try {
//			mailService.sendEmail(emailToSendTo);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}
	
	public List<ShiftSwap> listAllSwaps(){
		return shiftSwapRepository.findMySwaps();
	}

}