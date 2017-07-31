package com.chinaglia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chinaglia.model.SwapOrig;

@Repository("swapRepository")
public interface SwapRepository extends JpaRepository<SwapOrig, Integer> {
	
    @Query("SELECT t.email FROM SwapOrig t where t.id = :id") 
    String findUsersEmail(@Param("id") int id);
    
    @Query("select s from SwapOrig s "
    		+ "where s.swappersEmail = :email or s.email = :email")
    List<SwapOrig> findMySwaps(@Param("email") String email);
    
    @Query("select s from SwapOrig s "
    		+ "where s.swappersEmail != :email and s.email != :email")
    List<SwapOrig> findOtherUsersSwaps(@Param("email") String email); 
    
    @Query("select s from SwapOrig s "
    		+ "where confirmed = 1 and swapConfirmed = 1 and"
    		+ "(approvedBy = 'none' and disapprovedBy = 'none')")
    List<SwapOrig> findConfirmedSwaps();
    
    @Query("select s from SwapOrig s "
    		+ "where s.swapDate = null and s.email != :email")
    List<SwapOrig> findAvailableSwaps(@Param("email") String email);    
    
    
}
