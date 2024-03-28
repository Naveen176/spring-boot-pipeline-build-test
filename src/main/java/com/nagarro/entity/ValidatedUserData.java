package com.nagarro.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ValidatedUserData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;

	private String name;
	private int age;
	private String gender;
	private String dob;
	private String nationality;
	private String verification_status;
	private LocalDateTime date_created;
	private LocalDateTime date_modified;

	public ValidatedUserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidatedUserData(int user_id,String name, int age, String gender, String dob, String nationality,
			String verification_status, LocalDateTime date_created, LocalDateTime date_modified) {
		super();
		this.user_id=user_id;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.dob = dob;
		this.nationality = nationality;
		this.verification_status = verification_status;
		this.date_created = date_created;
		this.date_modified = date_modified;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getVerification_status() {
		return verification_status;
	}

	public void setVerification_status(String verification_status) {
		this.verification_status = verification_status;
	}

	public LocalDateTime getDate_created() {
		return date_created;
	}

	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}

	public LocalDateTime getDate_modified() {
		return date_modified;
	}

	public void setDate_modified(LocalDateTime date_modified) {
		this.date_modified = date_modified;
	}
}
