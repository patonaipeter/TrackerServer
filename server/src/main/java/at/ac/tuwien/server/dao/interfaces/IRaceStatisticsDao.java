package at.ac.tuwien.server.dao.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.RaceStatistics;
import at.ac.tuwien.server.domain.User;

public interface IRaceStatisticsDao {

	
	@Transactional
	public void saveRaceStats(RaceStatistics stat);
	
	@Transactional
	public List<RaceStatistics> retrieveRaceStatisticsForRace(Integer raceid);
	@Transactional
	public RaceStatistics retrieveRaceStatisticsForRaceAndUser(Race race,
			User user);
	
}
