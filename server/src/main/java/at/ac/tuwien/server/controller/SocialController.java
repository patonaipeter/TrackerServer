/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.ac.tuwien.server.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Message;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.MsgDTO;
import at.ac.tuwien.server.domain.dtos.MsgListDTO;
import at.ac.tuwien.server.domain.dtos.RaceDTO;
import at.ac.tuwien.server.domain.dtos.RaceListDTO;
import at.ac.tuwien.server.domain.dtos.UserDTO;
import at.ac.tuwien.server.domain.dtos.UserListDTO;
import at.ac.tuwien.server.service.interfaces.IRaceService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@Controller
@RequestMapping("/*")
public class SocialController {
	
	private static final Logger logger = LoggerFactory.getLogger(SocialController.class);
	@Autowired
	IUserService userService;
	@Autowired
	IRaceService raceService;
	
	@Transactional
	@RequestMapping(value="listusers", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody UserListDTO listUsers(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		List<User> users = userService.retrieveAllUsers();
		
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for(User u : users){
			UserDTO dto = new UserDTO();
			dto.setEmail(u.getEmail());
			dto.setId(u.getId());
			dto.setLast_activity_date(u.getLast_activity_date().getTime());
			dto.setNumOfFriends(u.getFirends().size());
			dto.setRegister_date(u.getRegister_date().getTime());
			dto.setScore(u.getScore());
			dto.setUsername(u.getUsername());
			
			dtos.add(dto);
		}
		UserListDTO liste = new UserListDTO(dtos);
		
		return liste;
		
	}
	
	@Transactional
	@RequestMapping(value="listfriends", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody UserListDTO listFriends(@RequestBody LinkedMultiValueMap<String, String> requestData) {

		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User user = userService.getUser(username, password);
		
		List<User> users = user.getFirends();
		
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for(User u : users){
			UserDTO dto = new UserDTO();
			dto.setEmail(u.getEmail());
			dto.setId(u.getId());
			dto.setLast_activity_date(u.getLast_activity_date().getTime());
			dto.setNumOfFriends(u.getFirends().size());
			dto.setRegister_date(u.getRegister_date().getTime());
			dto.setScore(u.getScore());
			dto.setUsername(u.getUsername());
			
			dtos.add(dto);
		}
		UserListDTO liste = new UserListDTO(dtos);
		
		return liste;
	}
	
	@Transactional
	@RequestMapping(value="listnearusers", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody UserListDTO listNearUsers(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		Double longitude = Double.parseDouble(requestData.getFirst("longitude"));
		Double latitude = Double.parseDouble(requestData.getFirst("latitude"));
		Double radius = Double.parseDouble(requestData.getFirst("radius"));
		User user = userService.getUser(username, password);
		
		//TODO more sophisticated login check
		if(user == null) return null;
		
		List<User> users = userService.retrieveNearUsers(user,longitude,latitude,radius);
		
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for(User u : users){
			UserDTO dto = new UserDTO();
			Location loc = userService.getLastPositionOfUser(u);
			dto.setLastLatitude(loc.getLatitude());
			dto.setLastLongitude(loc.getLongitude());
			
			dto.setEmail(u.getEmail());
			dto.setId(u.getId());
			dto.setLast_activity_date(u.getLast_activity_date().getTime());
			dto.setNumOfFriends(u.getFirends().size());
			dto.setRegister_date(u.getRegister_date().getTime());
			dto.setScore(u.getScore());
			dto.setUsername(u.getUsername());
			
			dtos.add(dto);
		}
		UserListDTO liste = new UserListDTO(dtos);
		
		return liste;
		
	}
	
	@Transactional
	@RequestMapping(value="listmessages", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody MsgListDTO listMessages(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User u = userService.getUser(username, password);
		
		List<Message> messages = userService.retrieveAllMessagesForUser(u);
		
		List<MsgDTO> dtos = new ArrayList<MsgDTO>();
		for(Message m : messages){
			MsgDTO dto = new MsgDTO();
			
			dto.setId(m.getId());
			dto.setSender(m.getSender().getUsername());
			dto.setSentDate(m.getSentDate().getTime());
			dto.setText(m.getMsgText());
			
			dtos.add(dto);
		}
		MsgListDTO liste = new MsgListDTO();
		liste.setMsgList(dtos);
		
		return liste;
		
	}
	
	@Transactional
	@RequestMapping(value="listfriendrequests", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody MsgListDTO listFriendRequests(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User u = userService.getUser(username, password);
		
		List<Message> messages = userService.retrieveAllFriendRequestsForUser(u);
		
		List<MsgDTO> dtos = new ArrayList<MsgDTO>();
		for(Message m : messages){
			MsgDTO dto = new MsgDTO();
			
			dto.setId(m.getId());
			dto.setSender(m.getSender().getUsername());
			dto.setSenderId(m.getSender().getId());
			dto.setSentDate(m.getSentDate().getTime());
			dto.setText(m.getMsgText());
			
			dtos.add(dto);
		}
		MsgListDTO liste = new MsgListDTO();
		liste.setMsgList(dtos);
		
		return liste;
		
	}
	
	@Transactional
	@RequestMapping(value="friendrequest", method=RequestMethod.POST)
	public @ResponseBody String addFriend(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		Integer id = Integer.parseInt(requestData.getFirst("friendid"));
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		
		User u = userService.getUser(username, password);
		User friend = userService.getUserById(id);
		
		//send message
		userService.sendFriendRequest(u,friend);
		
		return "success";
	}
	
	
	@Transactional
	@RequestMapping(value="acceptfriendrequest", method=RequestMethod.POST)
	public @ResponseBody String acceptFriend(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		Integer id = Integer.parseInt(requestData.getFirst("messageid"));
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		
		User user = userService.getUser(username, password);
		Message msg = userService.getMessageById(id);
		User friend = msg.getSender();
		
		userService.addFriend(user,friend);
		userService.deleteMsgById(msg.getId());
		return "success";
	}
	
	@Transactional
	@RequestMapping(value="denyfriendrequest", method=RequestMethod.POST)
	public @ResponseBody String denyFriend(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		Integer id = Integer.parseInt(requestData.getFirst("messageid"));
		userService.deleteMsgById(id);
		
		return "success";
	}
	
	@Transactional
	@RequestMapping(value="listraces", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody RaceListDTO listRaces(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User u = userService.getUser(username, password);
		
		Set<Race> races = raceService.retrieveAllRacesForUser(u);
		
		List<RaceDTO> dtos = new ArrayList<RaceDTO>();
		for(Race r : races){
			RaceDTO dto = new RaceDTO();
			
			dto.setId(r.getId());
			dto.setName(r.getRaceName());
			dto.setLength(r.getDistance());
			
			dtos.add(dto);
		}
		RaceListDTO liste = new RaceListDTO();
		liste.setRaceList(dtos);
		
		return liste;
		
	}
	
	@Transactional
	@RequestMapping(value="listjoinableraces", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody RaceListDTO listjoinableRaces(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User u = userService.getUser(username, password);
		
		Set<Race> races = raceService.retrieveAllJoinableRacesForUser(u);
		
		List<RaceDTO> dtos = new ArrayList<RaceDTO>();
		for(Race r : races){
			RaceDTO dto = new RaceDTO();
			
			dto.setId(r.getId());
			dto.setName(r.getRaceName());
			dto.setLength(r.getDistance());
			
			dtos.add(dto);
		}
		RaceListDTO liste = new RaceListDTO();
		liste.setRaceList(dtos);
		
		return liste;
		
	}
	
	@Transactional
	@RequestMapping(value="initnewrace", method=RequestMethod.POST)
	public @ResponseBody String initnewRace(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		String raceName = requestData.getFirst("racename");
		String userIdString = requestData.getFirst("useridstring"); //the id-s of the invited users comma separated
		User u = userService.getUser(username, password);
		List<String> userids = null;
		if(!userIdString.equals("")){
			userids = new ArrayList<String>(Arrays.asList(userIdString.split(",")));
			
		}
		Integer raceId = raceService.sendRaceInvitation(u , userids, raceName);
		return ""+raceId;
	}
	
	@Transactional
	@RequestMapping(value="wherewasi", method=RequestMethod.POST)
	public @ResponseBody String whereWasI(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		Long time = Long.parseLong(requestData.getFirst("time")); 
		
		User u = userService.getUser(username, password);
		Location loc = userService.getUserLocationForDate(u,time);
		
		//longitude,latitude
		if(loc == null){
			return "error";
		}
		return ""+loc.getLongitude()+","+loc.getLatitude();
	}
	
	@Transactional
	@RequestMapping(value="addracelocation", method=RequestMethod.POST)
	public @ResponseBody String addRaceLocation(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		String raceid = requestData.getFirst("raceid");
		String longitude = requestData.getFirst("longitude");
		String latitude = requestData.getFirst("latitude");
		String altitude = requestData.getFirst("altitude");
		
		User u = userService.getUser(username, password);
		
		//todo more clever login check
		if(u == null) return null;
		Location loc = new Location();
		loc.setLatitude(Double.parseDouble(latitude));
		loc.setLongitude(Double.parseDouble(longitude));
		loc.setAltitude(Double.parseDouble(altitude));
		loc.setTimestamp(new Date());
		loc.setUser(u);
		raceService.setRaceLocation(Integer.parseInt(raceid), loc);
		
		return "success";
	}
		
}

