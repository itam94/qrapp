package com.mat.rest;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mat.qrcode.Database;
import com.mat.qrcode.Room;

@Path("/room")
@Consumes(" application/json")
@Produces("application/json")
public class RoomService {
	
	Database database = new Database();
	JsonNodeFactory factory = JsonNodeFactory.instance;
	ObjectNode root = factory.objectNode();
	ObjectMapper mapper = new ObjectMapper();
	
	@POST
	@Path("/{login}/addNewRoom")
	public Response addNewRoom(@PathParam("login") String login, InputStream incomingData){
		String error = "";
		try{//tutaj
		Room room = (Room) mapper.readValue(incomingData, Room.class);
		
		database.addRoom(room,login);
		root.put("Response", "roomAdded");
		return Response.status(202).entity(mapper.writeValueAsString(root)).build();
		}catch (Exception e){
			error = e.toString();
		}
		return Response.status(202).entity("something went wrong").build();
		
	}

}
