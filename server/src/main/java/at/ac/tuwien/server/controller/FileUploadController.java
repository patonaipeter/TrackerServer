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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.ac.tuwien.server.service.ILocationService;
import at.ac.tuwien.server.service.IUserService;

@Controller
public class FileUploadController {
	
	@Autowired
	ILocationService locationService;
	@Autowired
	IUserService userService; 
	
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
//    @RequestMapping(value = "sendLogFile", method=RequestMethod.POST, headers="Content-Type=multipart/form-data")
//    public @ResponseBody String handleFormUpload(@RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
//
//        if (!file.isEmpty()) {
//        	byte[] bytes = null;
//            try {
//				bytes = file.getBytes();
//			} catch (IOException e) {
//				logger.info("error processing uploaded file", e);
//			}
//           return "file upload received! Name:[" + description + "] Size:[" + bytes.length + "]";
//       } else {
//    	   return "file upload failed!";
//       }
//    }
    
    @RequestMapping(value="sendLogFile", method=RequestMethod.POST)
	public @ResponseBody String sendMessageMap(@RequestBody MultiValueMap<String, Object> map) {

    	System.out.println("something received");
    	
    	int nrOfFiles = Integer.parseInt(map.getFirst("nrOfFiles").toString());
        String username = map.getFirst("username").toString();
        String password = map.getFirst("password").toString();
        String fileType = map.getFirst("fileType").toString();
        
        List<FileSystemResource> files = new ArrayList<FileSystemResource>();
        
        for(int i = 1;i < nrOfFiles;i++ ){
        	files.add((FileSystemResource) map.getFirst("file"+i));
        }
    	
        for(FileSystemResource f : files){
        	File gpxFile = f.getFile();
        	//parse file and save it to db
        	locationService.parseAndSaveGPX(gpxFile, userService.getUser(username, password));
        }
        
    	System.out.println("something received");
    	return "success";
    }

}
