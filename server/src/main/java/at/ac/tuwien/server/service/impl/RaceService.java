package at.ac.tuwien.server.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.Constants;
import at.ac.tuwien.server.dao.ILocationDao;
import at.ac.tuwien.server.dao.IMessageDao;
import at.ac.tuwien.server.dao.IRaceDao;
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Message;
import at.ac.tuwien.server.domain.MessageType;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.service.interfaces.IRaceService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@Service("raceService")
public class RaceService implements IRaceService {

	private static final int NOIDSET = -1;
	@Autowired
	IRaceDao raceDao;
	@Autowired
	ILocationDao locationDao;
	@Autowired
	IUserService userService;
	@Autowired
	IMessageDao messageDao;
	
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

	@Override
	@Transactional
	public Set<Race> retrieveAllRacesForUser(User u) {
		return u.getRaces();
	}

	@Override
	@Transactional
	public Set<Race> retrieveAllJoinableRacesForUser(User u) {
		List<Message> messages =  userService.retrieveAllMessagesForUser(u);
		Set<Race> races = new TreeSet<Race>();
		for(Message m : messages){
			if(m.getMsgType().equals(MessageType.RACEINVITATION)){
				races.add(this.getRaceById(Integer.parseInt(m.getMsgText())));
			}
		}
		return races;
	}

	@Transactional
	public Race getRaceById(Integer id) {
		return raceDao.getRaceById(id);
	}

	@Override
	@Transactional
	public Integer sendRaceInvitation(User u, List<String> userids, String raceName) {
		
		Race race = raceDao.saveNewRace(raceName);
		race.addParticipant(u);
		if(userids != null){
			for(String idString : userids){
				Integer id = Integer.parseInt(idString);
				User receiver =  userService.getUserById(id);
				//TODO
				race.addParticipant(receiver);

				Message m = new Message();
				m.setMsgType(MessageType.RACEINVITATION);
				m.setSentDate(new Date());
				m.setSender(u);
				m.setReceiver(receiver);
				m.setMsgText(""+race.getId());
				messageDao.saveMsg(m);
			}
		}
		//update
		raceDao.saveRace(race);
		return race.getId();
	}

	@Override
	@Transactional
	public void setRaceLocation(int id, Location loc) {
		Race race;
		race = this.getRaceById(new Integer(id));
		locationDao.saveLocation(loc);
		race.addLocation(loc);
		raceDao.saveRace(race);
		
	}

}
