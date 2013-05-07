package at.ac.tuwien.server.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import at.ac.tuwien.server.dao.interfaces.ILocationDao;
import at.ac.tuwien.server.dao.interfaces.IRaceDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Message;
import at.ac.tuwien.server.domain.MessageType;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.ILocationService;
import at.ac.tuwien.server.service.interfaces.IUserService;
import at.ac.tuwien.server.service.stats.StatisticsHelper;
import at.ac.tuwien.server.utils.GpxParser;

@Service("LocationService")
public class LocationService implements ILocationService {

	@Autowired
	ILocationDao locationDao;
	@Autowired
	IRaceDao raceDao;
	@Autowired
	IUserService userService;

	
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
			
			//update race statistics (distance elevation avgspeed)
			defaultLoggingRace = StatisticsHelper.updateRaceStats(defaultLoggingRace, 
											 locations,
											 locationDao.getLastLocationOfUser(defaultLoggingRace, u),
											 locationDao.getFirstLocationOfUser(defaultLoggingRace, u));
			
			try {
				locationDao.saveLocations(locations);
			} catch (ConstraintViolationException e) {
				// TODO: handle exception
				//trying to save locations data are already in the db, nothing to worry about
			}
			
					
			//add
			defaultLoggingRace.setLocations(locations);
			
			
			raceDao.saveRace(defaultLoggingRace);

			
			Location loc = (Location) locations.toArray()[0];
			List<User> users = userService.retrieveNearUsers(loc.getUser(),loc.getLongitude(),loc.getLatitude(),0.1); //todo change radius
			List<User> friends = new ArrayList<User>();
			for(User friend : users){
				if(loc.getUser().getFirends().contains(friend)) friends.add(friend);
			}
			
			if(!users.isEmpty()){
				
				for(User iUser : users){
					//send message to users
					userService.sendNearToYouNotification(this.createNearToYouMessage(loc.getUser(), iUser));
					
					//send message from every user 
					userService.sendNearToYouNotification(this.createNearToYouMessage(iUser,loc.getUser()));
					
				}
				
				
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Message createNearToYouMessage(User from, User to){
		Message msg = new Message();
		msg.setMsgText("near to you");
		msg.setMsgType(MessageType.NEARTOYOU);
		msg.setSentDate(new Date());
		msg.setSender(from);
		msg.setReceiver(to);
		msg.setUnread(true);
		
		return msg;
		
	}

}
