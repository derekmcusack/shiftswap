package com.chinaglia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinaglia.model.ShiftSwap;
import com.chinaglia.model.SwapOrig;

@Repository("shiftSwapRepository")
public interface ShiftSwapRepository extends JpaRepository<ShiftSwap, Integer> {
	SwapOrig findUserByEmail(String email);
}
