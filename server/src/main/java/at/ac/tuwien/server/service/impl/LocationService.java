package at.ac.tuwien.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import at.ac.tuwien.server.dao.interfaces.ILocationDao;
import at.ac.tuwien.server.dao.interfaces.IRaceDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.ILocationService;
import at.ac.tuwien.server.service.stats.StatisticsHelper;
import at.ac.tuwien.server.utils.GpxParser;

@Service("LocationService")
public class LocationService implements ILocationService {

	@Autowired
	ILocationDao locationDao;
	@Autowired
	IRaceDao raceDao;

	
	@Override
	@Transactional
	public void parseAndSaveGPX(MultipartFile f, User u) {
		//Parse GPX file
		//parse locations
		Set<Location> locations;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(f.getBytes());
			
			locations = GpxParser.readTrack(bis);
			Race defaultLoggingRace = raceDao.getDefaultRaceForUser(u);
			
			//set default track for each
			for(Location l : locations){
				l.setRace(defaultLoggingRace);
				l.setUser(u);
			}
			locationDao.saveLocations(locations);
			
			//update race statistics (distance elevation avgspeed)
			defaultLoggingRace = StatisticsHelper.updateRaceStats(defaultLoggingRace, 
											 locations,
											 locationDao.getLastLocationOfUser(defaultLoggingRace, u),
											 locationDao.getFirstLocationOfUser(defaultLoggingRace, u));
			
			//add
			defaultLoggingRace.setLocations(locations);
			
			
			raceDao.saveRace(defaultLoggingRace);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
