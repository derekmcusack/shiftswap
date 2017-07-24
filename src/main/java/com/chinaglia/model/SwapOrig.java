package com.chinaglia.model;



import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "swaporiginator")
public class SwapOrig implements Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 6303331429337067875L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "swap_origid")
	private int id;
	
	@Column(name = "Date")
	@NotEmpty(message = "*Please enter the date")
	private String date;	
	
	@Column(name = "StartTime")
	@NotEmpty(message = "*Please enter the start time")
	private String startTime;
	
	@Column(name = "FinishTime")
	@NotEmpty(message = "*Please enter the finish time")
	private String finishTime;
	
	@Column(name = "confirmed")
	private int confirmed;
	
	@Column(name = "note")
	@NotEmpty(message = "*Please enter details of shift you wish to swap with")
	private String note;

	@Column(name = "email")
	private String email;

	@Column(name = "swap_date")
	private String swapDate;		
	
	@Column(name = "swap_startTime")
	private String swapStartTime;
	
	@Column(name = "swap_finishTime")
	private String swapFinishTime;

	@Column(name = "swap_confirmed")
	private int swapConfirmed;
	
	@Column(name = "swap_note")
	private String swapNote;
	
	@Column(name = "approvedBy")
	private int approvedBy;
	
	@Column(name = "disapprovedBy")
	private int disapprovedBy;	
	
	@Column(name = "swap_email")
	private String swappersEmail = "default";	
	
	//these are "trigger" fields which will be updated every time a user logs in
	//they will be used to handle whether users should be allowed to accept or confirm swaps
	private String isConfirmed;
	private String isOriginator;
	
	public String getIsConfirmed() {
		return isConfirmed;
	}
	public void setIsConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public String getIsOriginator() {
		return isOriginator;
	}
	public void setIsOriginator(String isOriginator) {
		this.isOriginator = isOriginator;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSwapDate() {
		return swapDate;
	}

	public void setSwapDate(String swapDate) {
		this.swapDate = swapDate;
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

	public String getSwappersEmail() {
		return swappersEmail;
	}

	public void setSwappersEmail(String swappersEmail) {
		this.swappersEmail = swappersEmail;
	}

//	public int getSwapOriginalId() {
//		return swapOriginalId;
//	}
//
//	public void setSwapOriginalId(int swapOriginalId) {
//		this.swapOriginalId = swapOriginalId;
//	}
      

	

}
