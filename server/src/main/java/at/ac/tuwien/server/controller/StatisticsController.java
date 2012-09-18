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

import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.StatisticsDTO;
import at.ac.tuwien.server.service.interfaces.IUserService;

@Controller
@RequestMapping("/*")
public class StatisticsController {
	
	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	@Autowired
	IUserService userService;
	
	@Transactional
	@RequestMapping(value="statistics", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody StatisticsDTO retrieveXMLStats(@RequestBody LinkedMultiValueMap<String, String> credentials) {
		
		System.out.println("retrieving staticstics for " + credentials.getFirst("username") + " " + credentials.getFirst("password"));
		logger.info("retrieving staticstics for " + credentials.getFirst("username") + " " + credentials.getFirst("password"));
		
		String username = credentials.getFirst("username");
		String pass = credentials.getFirst("password");
		
		User u = userService.getUser(username, pass);
		
		//TODO Login Failed -> message to client
		if(u == null) return null;
		
		
		StatisticsDTO stats = userService.getStatisticsForUser(u);
		return stats;
	}

	
}

