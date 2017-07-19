package com.chinaglia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.chinaglia.model.ShiftSwap;

@Repository("shiftSwapRepository")
public interface ShiftSwapRepository extends JpaRepository<ShiftSwap, Integer> {

//    String MYSWAPSQUERY = "SELECT orig, swap from SwapOrig orig inner join orig.shiftSwapInfo swap";
//	
//	@Query(MYSWAPSQUERY)
//    List<ShiftSwap> findMySwaps();	
}