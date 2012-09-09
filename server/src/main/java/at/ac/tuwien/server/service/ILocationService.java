package at.ac.tuwien.server.service;

import java.io.File;

import at.ac.tuwien.server.domain.User;

public interface ILocationService {

	public void parseAndSaveGPX(File f, User u);
	
}
