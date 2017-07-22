package com.chinaglia.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chinaglia.model.ShiftSwap;
import com.chinaglia.model.SwapOrig;
import com.chinaglia.repository.SwapRepository;

@Service("swapService")
public class SwapServiceImpl implements SwapService{

	@Autowired
	private SwapRepository swapRepository;	
	
	@Autowired
	private EntityManager entityManager;
	
	private Session getSession(){
		return entityManager.unwrap(Session.class);
	}
	
	@Override
	public void saveSwap(SwapOrig swapRequest){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		swapRequest.setEmail(email);
		swapRepository.save(swapRequest);
	}

	@Override
	public List<SwapOrig> listAllSwaps() {
		return swapRepository.findAll();
	}
	
	@Override
	public SwapOrig getSwapOrigById(int id){
		Session session = getSession();	
		SwapOrig swapOrig = session.load(SwapOrig.class, id);
		return swapOrig;
	}
        

	@Override
	public void saveShiftSwap(SwapOrig swapOrig, String emailToSendTo){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		swapOrig.setSwappersEmail(email);
		swapRepository.save(swapOrig);
		
		//send email notification - ***commented out for testing purposes***
//		try {
//			mailService.sendEmail(emailToSendTo);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}	
}
