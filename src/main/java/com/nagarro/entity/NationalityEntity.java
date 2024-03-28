package com.nagarro.entity;

import java.util.List;

public class NationalityEntity {

	private int count;
	private String name;
	private List<CountryInfoEntity> country;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CountryInfoEntity> getCountry() {
		return country;
	}

	public void setCountry(List<CountryInfoEntity> country) {
		this.country = country;
	}
}