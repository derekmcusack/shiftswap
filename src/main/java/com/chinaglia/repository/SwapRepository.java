package com.chinaglia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chinaglia.model.SwapOrig;

@Repository("swapRepository")
public interface SwapRepository extends JpaRepository<SwapOrig, Integer> {
	
    @Query("SELECT t.email FROM SwapOrig t where t.id = :id") 
    String findUsersEmail(@Param("id") int id);
}
