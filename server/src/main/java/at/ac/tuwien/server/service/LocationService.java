package at.ac.tuwien.server.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.server.dao.ILocationDao;
import at.ac.tuwien.server.dao.IRaceDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.User;

@Service("LocationService")
public class LocationService implements ILocationService {

	@Autowired
	ILocationDao locationDao;
	@Autowired
	IRaceDao raceDao;
	
	@Override
	public void parseAndSaveGPX(File f, User u) {
		//Parse GPX file
		
		//
		Location loc = new Location();
		loc.setLatitude(new Long(1));
		loc.setLongitude(new Long(2));
//		loc.setRace(raceDao.getRaceForUser());
		
		
	}

}
