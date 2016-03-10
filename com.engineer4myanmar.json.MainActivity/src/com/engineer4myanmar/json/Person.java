package com.engineer4myanmar.json;

public class Person {
	private Integer id = null;
	private String name;
	private String password;
	private String email;
	private String phoneno;
	private String dob;
	private String profession;
	private String city;
	
	public Person(){
		super();
	}
	
	public Person(String name, String password, String email, String phoneno,
			String dob, String profession, String city) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
		this.phoneno = phoneno;
		this.dob = dob;
		this.profession = profession;
		this.city = city;
	}
	
	
	//getters & setters....
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

}
