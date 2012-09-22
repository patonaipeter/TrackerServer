package at.ac.tuwien.server.dao;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.User;

public interface ILocationDao {

	@Transactional
	public void saveLocation(Location location);
	
	@Transactional
	public void saveLocations(Set<Location> locations);

	@Transactional
	public Location getLastLocation(Race defaultLoggingRace);
	
	@Transactional
	public Location getFirstLocation(Race defaultLoggingRace);
	
	@Transactional
	public List<User> getNearUsers(Double longitude, Double latitude, Double radius,
			Long timeinterval);

	@Transactional
	public Location getLastPositionOfUser(User u);

	@Transactional
	public Location getUserLocationForDate(User u, Long time);
		
}
