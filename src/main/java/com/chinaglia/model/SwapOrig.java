package com.chinaglia.model;

import java.time.LocalDateTime;
import java.util.Set;

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
@Table(name = "swaporiginator")
public class SwapOrig {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "swapOrigID")
	private int id;
	@Column(name = "StartTime")
	@NotEmpty(message = "*Please enter the start time")
	private LocalDateTime startTime;
	@Column(name = "FinishTime")
	@NotEmpty(message = "*Please enter the finish time")
	private LocalDateTime finishTime;
	@Column(name = "confirmed")
	private int confirmed;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "users", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "userID"))


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

}
