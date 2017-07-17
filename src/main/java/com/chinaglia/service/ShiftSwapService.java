package com.chinaglia.service;

import java.util.List;

import com.chinaglia.model.ShiftSwap;

public interface ShiftSwapService {

	public void saveShiftSwap(ShiftSwap shiftSwap, String emailToSendTo);
	public List<ShiftSwap> listAllSwaps();
}
