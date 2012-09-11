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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import at.ac.tuwien.server.service.ILocationService;
import at.ac.tuwien.server.service.IUserService;

@Controller
public class FileUploadController {
	
	@Autowired
	ILocationService locationService;
	@Autowired
	IUserService userService; 
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	

    @Transactional
    @RequestMapping(value = "sendLogFile", method=RequestMethod.POST, headers="Content-Type=multipart/form-data")
    public @ResponseBody String handleFormUpload(@RequestParam("description") String description, 
    											 @RequestParam("username") String username,
    											 @RequestParam("password") String password,
    											 @RequestParam("file") MultipartFile file,
    											 @RequestParam("fileName") String fileName) {
    	if(fileName.contains("Race")){
    		//do something different
    	}else{
    		
    		locationService.parseAndSaveGPX(file, userService.getUser(username, password));
    		
    		
    	}
    	return "success";
    }

}
