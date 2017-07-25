package com.chinaglia.service;

import static com.google.common.collect.Lists.newArrayList;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;

@Service("myMailService")
public class MyMailServiceImpl implements MyMailService{

	    @Autowired
	    private EmailService emailService;

	    public void sendAcceptedEmail(String emailAddress, String shiftDetails) throws UnsupportedEncodingException {
	        final Email email = DefaultEmail.builder()
	                .from(new InternetAddress("shiftswapapp@yourwork.com",
	                        "ShiftSwap App"))
	                .to(newArrayList(
	                        new InternetAddress(emailAddress,
	                        "You There")))
	                .subject("Your Shift Swap Has been accepted!")
	                .body("Shift Details: " + shiftDetails + "\r\n\r\nLog into the ShiftSwap App to check the status of your swap!")
	                .encoding("UTF-8").build();

	        emailService.send(email);
	    }
	    
	    public void sendConfirmedEmail(String emailAddress, String shiftDetails) throws UnsupportedEncodingException {
	        final Email email = DefaultEmail.builder()
	                .from(new InternetAddress("shiftswapapp@yourwork.com",
	                        "ShiftSwap App"))
	                .to(newArrayList(
	                        new InternetAddress(emailAddress,
	                        "You There")))
	                .subject("Your Shift Swap Has been confirmed by the other party!")
	                .body("Shift details: " + shiftDetails + "\r\n\r\nLog into the ShiftSwap App to check the status of your swap!")
	                .encoding("UTF-8").build();

	        emailService.send(email);
	    }	    
	   
	    public void sendDisapprovedEmail(String emailAddress, String secondEmail, String shiftDetails) throws UnsupportedEncodingException{
	        final Email email = DefaultEmail.builder()
	                .from(new InternetAddress("shiftswapapp@yourwork.com",
	                        "ShiftSwap App"))
	                .to(newArrayList(
	                        new InternetAddress(emailAddress,
	                        "You There")))
	                .subject("Your Shift Swap Has been disapproved by Admin - sorry!")
	                .body("Shift Details: " + shiftDetails + "\r\n\r\nLog into the ShiftSwap App to check the status of your swap!")
	                .encoding("UTF-8").build();

	        emailService.send(email);
	        
	        final Email nextEmail = DefaultEmail.builder()
	                .from(new InternetAddress("shiftswapapp@yourwork.com",
	                        "ShiftSwap App"))
	                .to(newArrayList(
	                        new InternetAddress(secondEmail,"You There")))
	                .subject("Your Shift Swap Has been disapproved by Admin - sorry!")
	                .body("Shift Details: " + shiftDetails + "\r\n\r\nLog into the ShiftSwap App to check the status of your swap!")
	                .encoding("UTF-8").build();

	        emailService.send(nextEmail);
	    }
	    
	    public void sendApprovedEmail(String emailAddress, String secondEmail, String shiftDetails) throws UnsupportedEncodingException{
	        final Email email = DefaultEmail.builder()
	                .from(new InternetAddress("shiftswapapp@yourwork.com",
	                        "ShiftSwap App"))
	                .to(newArrayList(
	                        new InternetAddress(emailAddress,
	                        "You There")))
	                .subject("Your Shift Swap Has been approved by Admin!")
	                .body("Shift Details: " + shiftDetails + "\r\n\r\nLog into the ShiftSwap App to check the status of your swap!")
	                .encoding("UTF-8").build();

	        emailService.send(email);
	        
	        final Email nextEmail = DefaultEmail.builder()
	                .from(new InternetAddress("shiftswapapp@yourwork.com",
	                        "ShiftSwap App"))
	                .to(newArrayList(
	                        new InternetAddress(secondEmail,
	                        "You There")))
	                .subject("Your Shift Swap Has been approved by Admin!")
	                .body("Shift Details: " + shiftDetails + "\r\n\r\nLog into the ShiftSwap App to check the status of your swap!")
	                .encoding("UTF-8").build();

	        emailService.send(nextEmail);
	    }	    
	    
}	    