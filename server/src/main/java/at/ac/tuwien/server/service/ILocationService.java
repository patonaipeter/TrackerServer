package at.ac.tuwien.server.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import at.ac.tuwien.server.domain.User;

public interface ILocationService {

	@Transactional
	public void parseAndSaveGPX(MultipartFile f, User u);
	
}
