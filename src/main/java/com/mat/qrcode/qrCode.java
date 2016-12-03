package com.mat.qrcode;

import java.awt.Image;



public class qrCode {
	
	private String longitude;
	private String latitude;
	private String room;
	private String qrcode;
	

	public String getLongitude() {
		return this.longitude;
	}
	//no
	public String getQRCode() {
		return this.qrcode;
	}
	public String getLatitude() {
		return this.latitude;
	}

	public String getRoom() {
		return this.room;
	}
	


	public void setRoom(String room) {
		this.room = room;
	}

	public void setLocation(String longitude, String latitude) {
		this.latitude = latitude;
		this.longitude=longitude;
	}
	
	public void setQRCode(String qrcode){
		this.qrcode=qrcode;
	}


	



};