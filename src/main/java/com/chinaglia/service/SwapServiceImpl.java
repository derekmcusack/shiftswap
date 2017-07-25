package com.chinaglia.service;

import java.io.UnsupportedEncodingException;
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
	
	@Autowired
	private MyMailService mailService;
	
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
	public List<SwapOrig> listMySwaps(String email) {
		return swapRepository.findMySwaps(email);
	}	
	
	@Override
	public boolean isUserOriginator(int id, String email){
		SwapOrig one = swapRepository.findOne(id);
		if(one.getEmail().equals(email)){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public SwapOrig getSwapOrigById(int id){
		Session session = getSession();	
		SwapOrig swapOrig = session.load(SwapOrig.class, id);
		return swapOrig;
	}
        

	@Override
	public void saveShiftSwap(SwapOrig swapOrig, String emailToSendTo, String shiftDetails){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		swapOrig.setSwappersEmail(email);
		swapRepository.save(swapOrig);
		
		//send email notification - ***commented out for testing purposes***
//			try {
//			mailService.sendAcceptedEmail(emailToSendTo, shiftDetails);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}	
	}	

	@Override
	public void updateUsersSwapStatus(String email){
		List<SwapOrig> listMySwaps = swapRepository.findMySwaps(email);
		for(SwapOrig swapOrig : listMySwaps){
			if(email.equals(swapOrig.getEmail())){
				swapOrig.setIsOriginator("y");
				swapRepository.save(swapOrig);
			}else{
				swapOrig.setIsOriginator("n");
				swapRepository.save(swapOrig);
			}
			if((email.equals(swapOrig.getEmail()) && swapOrig.getConfirmed() == 1) ||
					(email.equals(swapOrig.getSwappersEmail()) && swapOrig.getSwapConfirmed() == 1)){
				swapOrig.setIsConfirmed("y");
				swapRepository.save(swapOrig);				
			}else{
				swapOrig.setIsConfirmed("n");
				swapRepository.save(swapOrig);				
			}
		}
		
	}
	
	@Override
	public void updateOtherSwapStatus(String email){		
		List<SwapOrig> listOtherUsersSwaps = swapRepository.findOtherUsersSwaps(email);
		for(SwapOrig swapOrig : listOtherUsersSwaps){
			swapOrig.setIsOriginator("n");
			swapRepository.save(swapOrig);
		}
	}
	
	@Override
	public List<SwapOrig> returnConfirmedSwaps(){
		List<SwapOrig> confirmedSwaps = swapRepository.findConfirmedSwaps();
		return confirmedSwaps;
	}
}
