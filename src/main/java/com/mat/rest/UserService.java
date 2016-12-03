package com.mat.rest;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.generator.qrcode.QRCodeGenerator;
import com.mail.sander.MailSender;
import com.mat.qrcode.Database;
import com.mat.qrcode.User;
import com.mat.qrcode.qrCode;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("/login")
@Consumes(" application/json")
@Produces("application/json")
public class UserService {
	Database database = new Database();
	JsonNodeFactory factory = JsonNodeFactory.instance;
	ObjectNode response = factory.objectNode();
	ObjectMapper mapper = new ObjectMapper();

	@GET
	@Path("/log/{login}")
	public Response getPassword(@PathParam("login") String login) {
		String haslo;
		//soc
		haslo = database.showUserPasswordMail(login,"password");
		try{
		if(haslo != ""){
			response.put("password", haslo);
			
			return Response.status(200).entity(mapper.writeValueAsString(response)).build();
		}else{
			response.put("password", "no such user");
			return Response.status(500).entity(mapper.writeValueAsString(response)).build();
		}
		}catch(Exception e){
			return Response.status(500).entity("something went wrong").build();
		}
		
	}
	
	
	
	@POST
	@Path("/register")
	public Response register(InputStream incomingData)   {
		try{
			boolean userExist;
			
			User usr = mapper.readValue(incomingData, User.class);
			
			userExist = database.createUserDatabase(usr.getLogin(),usr.getPassword(),usr.getMail());
			
			if(userExist){
				response.put("status", "userExists");
				return Response.status(500).entity(mapper.writeValueAsString(response)).build();

			}else{
				response.put("status", "succesfullyCreated");
				return Response.status(200).entity(mapper.writeValueAsString(response)).build();

			}
		  
		}catch(JsonParseException e1){
			
		} catch( JsonMappingException e2){
			
		}catch( IOException e3){
			
		}
		return Response.status(500).entity("something went wrong").build();

	}
	
	
	
}
