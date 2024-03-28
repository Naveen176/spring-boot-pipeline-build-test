package com.nagarro.entity;

public class ResultEntity {

	private String gender;
	private NameEntity name;
	private DateOfBirthEntity dob;
	private String nat;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public NameEntity getName() {
		return name;
	}

	public void setName(NameEntity name) {
		this.name = name;
	}

	public DateOfBirthEntity getDob() {
		return dob;
	}

	public void setDob(DateOfBirthEntity dob) {
		this.dob = dob;
	}

	public String getNat() {
		return nat;
	}

	public void setNat(String nat) {
		this.nat = nat;
	}
}