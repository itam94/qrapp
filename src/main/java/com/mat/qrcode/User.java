package com.mat.qrcode;

public class User {
	
	String password;
	String login;
	String mail;
	
	public String getPassword(){
		return this.password;
	}
	public String getLogin(){
		return this.login;
	}
	public String getMail(){
		return this.mail;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public void setLogin(String login){
		this.login = login;
	}
	public void setMail(String mail){
		this.mail = mail;
	}
}
