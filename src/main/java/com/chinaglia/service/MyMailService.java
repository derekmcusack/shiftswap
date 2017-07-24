package com.chinaglia.service;

import java.io.UnsupportedEncodingException;

public interface MyMailService {

	void sendAcceptedEmail(String emailAddress) throws UnsupportedEncodingException;
	void sendConfirmedEmail(String emailAddress) throws UnsupportedEncodingException;
}