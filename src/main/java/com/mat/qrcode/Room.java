package com.mat.qrcode;

public class Room {
	private String room;
	private String longitude1;
	private String latitude1;
	private String longitude2;
	private String latitude2;
	
	public String getRoom(){
		return this.room;
	}
	
	public String getLongitude1(){
		return this.longitude1;
	}
	
	public String getLatitude2(){
		return this.latitude2;
	}
	
	public String getLatitude1(){
		return this.latitude1;
	}
	
	public String getLongitude2(){
		return this.longitude2;
	}
	public void setRoom(String room){
		this.room = room;		
	}//ze
	public void setLongitude1(String longitude1){
		this.longitude1 = longitude1;
	}
	public void setLatitude2(String latitude2){
		this.latitude2 = latitude2;
	}
	public void setLatitude1(String latitude1){
		this.latitude1 = latitude1;
	}
	public void setLongitude2(String longitude2){
		this.longitude2 = longitude2;
	}
}
