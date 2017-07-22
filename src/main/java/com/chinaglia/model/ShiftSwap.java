package com.chinaglia.model;



import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "shiftswap")
public class ShiftSwap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2296804780039876348L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "shiftSwapID")
	private int id;
	
	@Column(name = "swapOrigID")
	private int swapOrigId;
	
	@Column(name = "date")
	@NotEmpty(message = "*Please enter the date")
	private String swapDate;		
	
	@Column(name = "startTime")
	@NotEmpty(message = "*Please enter the start time")
	private String swapStartTime;
	
	@Column(name = "finishTime")
	@NotEmpty(message = "*Please enter the finish time")	
	private String swapFinishTime;

	@Column(name = "confirmed")
	private int swapConfirmed;
	
	@Column(name = "note")
	@NotEmpty(message = "*Please enter details of shift you wish to swap with")	
	private String swapNote;
	
	@Column(name = "approvedBy")
	private int approvedBy;
	
	@Column(name = "disapprovedBy")
	private int disapprovedBy;	
	
	@Column(name = "email")
	private String swappersEmail;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSwapOrigId() {
		return swapOrigId;
	}
	public void setSwapOrigId(int swap_orig_id) {
		this.swapOrigId = swap_orig_id;
	}	
	public String getSwapDate() {
		return swapDate;
	}
	public void setSwapDate(String swapDate) {
		this.swapDate = swapDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSwapStartTime() {
		return swapStartTime;
	}
	public void setSwapStartTime(String swapStartTime) {
		this.swapStartTime = swapStartTime;
	}
	public String getSwapFinishTime() {
		return swapFinishTime;
	}
	public void setSwapFinishTime(String swapFinishTime) {
		this.swapFinishTime = swapFinishTime;
	}
	public String getSwapEmail() {
		return swappersEmail;
	}
	public void setSwapEmail(String swappersEmail) {
		this.swappersEmail = swappersEmail;
	}
	public int getSwapConfirmed() {
		return swapConfirmed;
	}
	public void setSwapConfirmed(int swapConfirmed) {
		this.swapConfirmed = swapConfirmed;
	}
	public String getSwapNote() {
		return swapNote;
	}
	public void setSwapNote(String swapNote) {
		this.swapNote = swapNote;
	}
	public int getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(int approvedBy) {
		this.approvedBy = approvedBy;
	}
	public int getDisapprovedBy() {
		return disapprovedBy;
	}
	public void setDisapprovedBy(int disapprovedBy) {
		this.disapprovedBy = disapprovedBy;
	}

}
