package com.mat.rest;

import java.io.InputStream;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mat.qrcode.Database;
import com.mat.qrcode.qrCode;
import com.generator.qrcode.QRCodeGenerator;
import com.mail.sander.MailSender;

@Path("/qrcode")
@Consumes("application/json")
@Produces(" application/json")
public class QRCodeService {
	
	
	Database database = new Database();
	JsonNodeFactory factory = JsonNodeFactory.instance;
	ObjectNode root = factory.objectNode();
	ObjectMapper mapper = new ObjectMapper();
	
	@POST
	@Path("/{login}/NewQRCode")

	public Response newQRPoint(@PathParam("login") String login ,InputStream incomingData) {
		
		try {
			
			qrCode qr = (qrCode)  mapper.readValue(incomingData, qrCode.class);
			QRCodeGenerator picture = new QRCodeGenerator(login);
			
			String id = database.insertQRCode(qr, login);
			if(!id.equals("")){
				root.put("Response", "pointAdded");
				picture.createQrCode(id);
				MailSender m = new MailSender(database.showUserPasswordMail(login,"email"),login);
				return Response.status(202).entity(mapper.writeValueAsString(root)).build();
			}else{
				root.put("Response", "cantAddPoint");
				return Response.status(202).entity(mapper.writeValueAsString(root)).build();
				}
			} catch (Exception e) {
			e.printStackTrace();
			return Response.status(202).entity("something went wrong"+e+ database.showUserPasswordMail(login,"mail")	).build();
			}
		
	
		
		
	}
		
		@POST
		@Path("/{login}/{id}/addLocation")
		public Response addLocation(@PathParam("login") String login,@PathParam("id") int id, InputStream incomingData){
			String JSONToString = "";
			boolean foundRoom = false;
			try {	
				
				qrCode qr = (qrCode)  mapper.readValue(incomingData, qrCode.class);
				JSONToString = mapper.writeValueAsString(qr);
				foundRoom = database.updateQRCode(qr, login, id);
			
			if(foundRoom){	
				root.put("Response", "foundRoom");
				return Response.status(202).entity(mapper.writeValueAsString(root)).build();
			}else{
				root.put("Response", "cantFindRoom");
				return Response.status(500).entity(mapper.writeValueAsString(root)).build();
			}
			} catch (Exception e) {
				JSONToString = e.toString();
				
			}
			return Response.status(500).entity("something wrong").build();
		}
		
		//moze
		
		
	
	@GET
	@Path("/{login}/{point}")
	public Response getQRPoint(@PathParam("login") String login,@PathParam("point") int point) throws JsonProcessingException {
		
		
		qrCode code = new qrCode();
		code = database.showQRCode(login, point);
		
		if(code==null){
			root.put("error", "noSuchQrCode");
			return Response.status(500).entity(mapper.writeValueAsString(root)).build();
		
		}else{
			String JSONToString = mapper.writeValueAsString(code);
			return Response.status(200).entity(JSONToString).build();
		}
		
		

	}
}
