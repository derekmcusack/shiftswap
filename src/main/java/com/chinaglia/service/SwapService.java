package com.chinaglia.service;

import com.chinaglia.model.SwapOrig;

public interface SwapService {
	public SwapOrig findUserByEmail(String email);
	public void saveSwap(SwapOrig swapRequest);
}
