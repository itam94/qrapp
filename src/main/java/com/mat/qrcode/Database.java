package com.mat.qrcode;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

public class Database {
	public String good;
	public String error;

	public Connection conn;

	public Database() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			error += "ldalal" + e.getMessage() + "lalalal";
		}

		try {
			String url = "jdbc:mysql://127.10.248.2:3306/grapps";
			String username = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
			String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {		

		}

	}

	public boolean createUserDatabases(String login, String password, String email) {
		boolean userExists = false; //asdgsad
		String createQuestonaire[] = new String[5];
		createQuestonaire[0] = "CREATE DATABASE " + login + ";";
		createQuestonaire[1] = "USE " + login + ";";
		createQuestonaire[2] = "CREATE TABLE IF NOT EXISTS qrPoints (id INTEGER PRIMARY KEY AUTO_INCREMENT, qrcode varchar(20), room varchar(20), longitude varchar(15), latitude varchar(15));";
		createQuestonaire[3] = "CREATE TABLE IF NOT EXISTS userData (login varchar(20), password varchar(20),email varchar(40));";
		createQuestonaire[4] = "CREATE TABLE IF NOT EXISTS house (room varchar(30), longitude1 varchar(20), latitude1 varchar(20), longitude2 varchar(20), latitude2 varchar(20));";
		
		if (conn == null){
			return true;
		}
		try {
			for (int i = 0; i < createQuestonaire.length; i++) {
				if(!userExists){
					PreparedStatement prepStmt = conn.prepareStatement(createQuestonaire[i]);
					prepStmt.execute();
				}
			}
			PreparedStatement prepeStmt = conn.prepareStatement("insert into userData values (?, ?, ?)");
			prepeStmt.setString(1, login);
			prepeStmt.setString(2, password);
			prepeStmt.setString(3, email);
			prepeStmt.execute();
		} catch (SQLException e) {
			userExists = true;
		}
		
		return userExists;
		
	}
	
	

	public String showUserPasswordMail(String login, String what) {
		String response = " ";
		String question = "Select * FROM userData;";
		
		
		try {
			PreparedStatement userDatabase = conn.prepareStatement("USE " + login + ";");
			userDatabase.execute();

			Statement prepStmt = conn.createStatement();

			ResultSet rs = prepStmt.executeQuery(question);
			while (rs.next()) {
				response = rs.getString(what);
			}
		} catch (SQLException e) {
			
			return response;
			
		}

		return response;
	}
	
public qrCode showQRCode(String login, int lookingObject) {
		
		qrCode qr = new qrCode();
		String question = "Select * FROM qrPoints";

		try {
			PreparedStatement userDatabase = conn.prepareStatement("USE " + login + ";");
			userDatabase.execute();

			Statement prepStmt = conn.createStatement();

			ResultSet rs = prepStmt.executeQuery(question);
			while (rs.next()) {
				
				if(rs.getInt("id")==lookingObject){
					qr.setQRCode(rs.getString("qrcode"));
					qr.setLocation(rs.getString("longitude"), rs.getString("latitude"));
					qr.setRoom(rs.getString("room"));
					return qr;
				}						
			}
		} catch (SQLException e) {

		}//tak
		return null;
	
	}
	
	public String insertQRCode(qrCode code, String login){
		String result="0";
		try{
		PreparedStatement userDatabase = conn.prepareStatement("USE " + login + ";");
		userDatabase.execute();
		
		
		PreparedStatement prepeStmt = conn.prepareStatement("insert into qrPoints values (NULL, ?, NULL, NULL,NULL)");
		prepeStmt.setString(1, code.getQRCode());
		prepeStmt.execute();

		
		Statement preplastid = conn.createStatement();
		ResultSet lastid = preplastid.executeQuery("SELECT MAX(id) AS id FROM qrPoints");
		while(lastid.next()){
		
		result = lastid.getInt("id")+"";
		}
		}catch (SQLException e){
			result = e.getErrorCode()+"";
		}
		return result;
	}
	
	public boolean updateQRCode(qrCode code, String login, int id) {
		try{
			PreparedStatement userDatabase = conn.prepareStatement("USE " + login + ";");
			userDatabase.execute();
			
			code.setRoom(this.findRoom(code.getLongitude(),code.getLatitude()));
			if(code.getRoom().equals("")){
				return false;
			}

			PreparedStatement prepeStmt = conn.prepareStatement("UPDATE qrPoints SET longitude = "+ code.getLongitude()+",latitude ="+ code.getLatitude()+", room = '"+code.getRoom()+"' WHERE id ="+ id);
		
			prepeStmt.execute();

		}catch (SQLException e){
			
		}
		
		return true;
		
	}	
	
	private String findRoom(String longitude, String latitude){
		String room = null;
		try{
			Double longitudeSrc, latitudeSrc;
			longitudeSrc = Double.parseDouble(longitude);
			latitudeSrc = Double.parseDouble(latitude);
			
			Statement preprooms= conn.createStatement();
			ResultSet rooms = preprooms.executeQuery("SELECT * FROM house");
			
			while (rooms.next()) {
				Double  longitude1, latitude1, longitude2, latitude2;
				longitude1 = Double.parseDouble(rooms.getString("longitude1"));
				longitude2 =Double.parseDouble(rooms.getString("longitude2"));
				latitude1 = Double.parseDouble(rooms.getString("latitude1"));
				latitude2 = Double.parseDouble(rooms.getString("latitude2"));
				if(longitudeSrc > longitude1 && longitudeSrc < longitude2 || longitudeSrc < longitude1 && longitudeSrc > longitude2){
					if(latitudeSrc < latitude1 && latitudeSrc > latitude2 || latitudeSrc > latitude1 && latitudeSrc < latitude2 ){
						return rooms.getString("room");
					}
				}
				
			}
			
		
		}catch (Exception e){
			
		}
		
		return "";
	}

	public void addRoom(Room room, String login) {
		try{
			PreparedStatement userDatabase = conn.prepareStatement("USE " + login + ";");
			userDatabase.execute();
			
			
			PreparedStatement prepeStmt = conn.prepareStatement("INSERT INTO house VALUES (?, ?, ?, ?, ?)");
			
			prepeStmt.setString(1, room.getRoom());
			prepeStmt.setString(2, room.getLongitude1());
			prepeStmt.setString(3, room.getLatitude1());
			prepeStmt.setString(4, room.getLongitude2());
			prepeStmt.setString(5, room.getLatitude2());
			
			prepeStmt.execute();			
			
			}catch (SQLException e){
				
			}
		
	}
	

}
