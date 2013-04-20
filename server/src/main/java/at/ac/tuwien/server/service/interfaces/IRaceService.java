package at.ac.tuwien.server.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;

public interface IRaceService {

	
	@Transactional
	public Double getAvgSpeedInRaces(User u);
	@Transactional
	public Set<Race> retrieveAllRacesForUser(User u);
	@Transactional
	public Set<Race> retrieveAllJoinableRacesForUser(User u);
	@Transactional
	public Integer sendRaceInvitation(User u, List<String> userids, String raceName);
	@Transactional
	public void setRaceLocation(int parseInt, Location loc);
	@Transactional
	void setRaceHistoricalLocation(int id, Location loc);
	
}
