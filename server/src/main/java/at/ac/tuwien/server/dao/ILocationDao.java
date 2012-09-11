package at.ac.tuwien.server.dao;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;

public interface ILocationDao {

	@Transactional
	public void saveLocation(Location location);
	
	@Transactional
	public void saveLocations(Set<Location> locations);

	@Transactional
	public Location getLastLocation(Race defaultLoggingRace);
	
	@Transactional
	public Location getFirstLocation(Race defaultLoggingRace);
		
}
