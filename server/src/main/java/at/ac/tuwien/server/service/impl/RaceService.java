package at.ac.tuwien.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.dao.ILocationDao;
import at.ac.tuwien.server.dao.IRaceDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.IRaceService;

@Service("raceService")
public class RaceService implements IRaceService {

	@Autowired
	IRaceDao raceDao;
	@Autowired
	ILocationDao locationDao;
	
	@Override
	@Transactional
	public Double getAvgSpeedInRaces(User u) {
		
		Double sumDistance = raceDao.getDistanceInRaces(u);
		Long sumTime = new Long(0);

		for(Race r : u.getRaces()){
			if(!r.getRaceName().equals(Constants.defaultRace)){
				Location first = locationDao.getFirstLocation(r);
				Location last = locationDao.getLastLocation(r);
				sumTime += last.getTimestamp().getTime() - first.getTimestamp().getTime();
			}
		}
		// km/h
		Double avgSpeed = new Double(sumDistance/(sumTime/(1000*60*60)));
		return avgSpeed;
	}

}
