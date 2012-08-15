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

import at.ac.tuwien.server.Message;
import at.ac.tuwien.server.dao.IUserDao;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.IUserService;

@Controller
@RequestMapping("/*")
public class RegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	IUserService userService;

	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		logger.info("Spring Android Showcase");
		return "home";
	}
	
	@RequestMapping(value="sendmessage", method=RequestMethod.POST, headers="Content-Type=text/plain")
	public @ResponseBody String sendMessage(@RequestBody String message) {
		logger.info("String message: " + message);
		return "String message received! Your message: " + message;
	}
	
	@RequestMapping(value="sendmessage", method=RequestMethod.POST, headers="Content-Type=application/json")
	public @ResponseBody String sendMessageJson(@RequestBody Message message) {
		logger.info("JSON message: " + message.toString());
		return "JSON message received! Your message: " + message.toString();
	}
	
	@RequestMapping(value="sendmessage", method=RequestMethod.POST, headers="Content-Type=application/xml")
	public @ResponseBody String sendMessageXml(@RequestBody Message message) {
		logger.info("XML message: " + message.toString());
		return "XML message received! Your message: " + message.toString();
	}
	
	@Transactional
	@RequestMapping(value="sendmessagemap", method=RequestMethod.POST)
	public @ResponseBody String sendMessageMap(@RequestBody LinkedMultiValueMap<String, String> map) {
//		Message message = new Message();
//		
//		try {
//			message.setId(Integer.parseInt(map.getFirst("id")));
//		} catch (NumberFormatException e) {
//			message.setId(0);
//		}
//		
//		message.setSubject(map.getFirst("subject"));
//		message.setText(map.getFirst("text"));
//
//		logger.info("Map message: " + message.toString());
//		return "Map message received! Your message: " + message.toString();
		
		//TODO check whether values are not null
		logger.info("sendMessageMap method called");
		User user = new User();
		user.setUsername(map.getFirst("username"));
		user.setPassword(map.getFirst("password"));
		user.setEmail(map.getFirst("email"));

		logger.info("New User object is created");
		try{
			
			userService.createUser(user);
			return "User registered Successfully!";
		}catch (Exception e) {
			logger.error(e.getMessage());
			return e.getMessage();
		}
		
	}

	
}

