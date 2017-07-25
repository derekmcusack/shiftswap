package com.chinaglia.service;

import java.io.UnsupportedEncodingException;

public interface MyMailService {

	void sendAcceptedEmail(String emailAddress, String shiftDetails) throws UnsupportedEncodingException;
	void sendConfirmedEmail(String emailAddress, String shiftDetails) throws UnsupportedEncodingException;
	void sendDisapprovedEmail(String emailAddress, String secondEmail, String shiftDetails) throws UnsupportedEncodingException;
	void sendApprovedEmail(String emailAddress, String secondEmail, String shiftDetails) throws UnsupportedEncodingException;
}