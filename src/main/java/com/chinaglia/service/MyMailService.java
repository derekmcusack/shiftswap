package com.chinaglia.service;

import java.io.UnsupportedEncodingException;

public interface MyMailService {

	void sendEmail(String emailAddress) throws UnsupportedEncodingException;

}