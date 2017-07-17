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
	@Column(name = "swapOrigID")
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

    @OneToOne(cascade = CascadeType.ALL) 
    @JoinColumn(name = "swapOrigID")  
	private ShiftSwap shiftSwapInfo;
	
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
      
	public ShiftSwap getShiftSwapInfo() {
		return shiftSwapInfo;
	}

	public void setShiftSwap(ShiftSwap shiftSwapInfo) {
		this.shiftSwapInfo = shiftSwapInfo;
	}
	

}
