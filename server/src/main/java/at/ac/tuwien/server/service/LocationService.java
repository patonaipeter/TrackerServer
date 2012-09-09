package at.ac.tuwien.server.service;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.dao.ILocationDao;
import at.ac.tuwien.server.dao.IRaceDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
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
		
		//create locations
		Location loc = new Location();
		loc.setLatitude(new Long(1));
		loc.setLongitude(new Long(2));
		
		//retrieve user's default race
		Race defaultLoggingRace = null;
		for(Race r : u.getRaces()){
			if(r.getRaceName().equals(Constants.defaultRace)){
				defaultLoggingRace = r;
				break;
			}
		}
		
		//add locationst to a list
		Set<Location> locations = new TreeSet<Location>();
		locations.add(loc);
		
		//add
		defaultLoggingRace.setLocations(locations);
		
	}

}
