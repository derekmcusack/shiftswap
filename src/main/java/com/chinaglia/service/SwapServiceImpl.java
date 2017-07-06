package com.chinaglia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.chinaglia.model.SwapOrig;
import com.chinaglia.repository.SwapRepository;

@Service("swapService")
public class SwapServiceImpl implements SwapService{

	@Autowired
	private SwapRepository swapRepository;	
	
	@Override
	public void saveSwap(SwapOrig swapRequest){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		swapRequest.setEmail(email);
		swapRepository.save(swapRequest);
	}

	@Override
	public SwapOrig findUserByEmail(String email) {
		return swapRepository.findUserByEmail(email);
	}

}
