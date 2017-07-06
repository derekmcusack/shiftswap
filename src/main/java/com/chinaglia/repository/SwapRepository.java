package com.chinaglia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinaglia.model.SwapOrig;

@Repository("swapRepository")
public interface SwapRepository extends JpaRepository<SwapOrig, Integer> {
	SwapOrig findUserByEmail(String email);
}
