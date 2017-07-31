package com.chinaglia.service;

import java.util.List;

import com.chinaglia.model.SwapOrig;

public interface SwapService {

	public void saveSwap(SwapOrig swapRequest);
	public List<SwapOrig> listAllSwaps();
	public void saveShiftSwap(SwapOrig swapOrig, String emailToSendTo, String shiftDetails);
	public SwapOrig getSwapOrigById(int id);
	public List<SwapOrig> listMySwaps(String email);
	public boolean isUserOriginator(int id, String email);
	public void updateUsersSwapStatus(String email);
	public void updateOtherSwapStatus(String email);
	public List<SwapOrig> returnConfirmedSwaps();
	public List<SwapOrig> listAvailableSwaps(String email);
}
