package com.chinaglia.model;



import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	@Column(name = "Date")
	@NotEmpty(message = "*Please enter the date")
	private String date;		
	@Column(name = "StartTime")
	@NotEmpty(message = "*Please enter the start time")
	private String startTime;
	@Column(name = "FinishTime")
	@NotEmpty(message = "*Please enter the finish time")	
	private String finishTime;
	@Column(name = "email")
	private String email;
	@Column(name = "confirmed")
	private int confirmed;
	@Column(name = "note")
	@NotEmpty(message = "*Please enter details of shift you wish to swap with")	
	private String note;
	@Column(name = "approvedBy")
	private int approvedBy;
	@Column(name = "disapprovedBy")
	private int disapprovedBy;
	

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "swaporiginator", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "email"))	
	
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
