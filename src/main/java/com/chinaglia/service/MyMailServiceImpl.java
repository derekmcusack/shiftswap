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

	    public void sendEmail(String emailAddress) throws UnsupportedEncodingException {
	        final Email email = DefaultEmail.builder()
	                .from(new InternetAddress("shiftswapapp@yourwork.com",
	                        "ShiftSwap App"))
	                .to(newArrayList(
	                        new InternetAddress(emailAddress,
	                        "You There")))
	                .subject("Your Shift Swap Has been accepted!")
	                .body("Log into the ShiftSwap App to check the status of your swap!")
	                .encoding("UTF-8").build();

	        emailService.send(email);
	    }
}	    