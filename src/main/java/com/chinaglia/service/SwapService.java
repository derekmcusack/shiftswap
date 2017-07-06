package com.chinaglia.service;

import java.util.List;

import com.chinaglia.model.SwapOrig;

public interface SwapService {
	public SwapOrig findUserByEmail(String email);
	public void saveSwap(SwapOrig swapRequest);
	public List<SwapOrig> listAllSwaps();
}
