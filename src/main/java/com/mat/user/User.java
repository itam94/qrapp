package com.mat.user;

public class User {
	String name;
	String password;
	String mail;
	
	public String getName(){
		return this.name;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getMail(){
		return this.mail;
	}
	
	void setName(String name){
		this.name = name;
	}
	
	void setPassword(String password){
		this.password = password;
	}
	
	void setMail(String mail){
		this.mail = mail;//sdf
	}
}
