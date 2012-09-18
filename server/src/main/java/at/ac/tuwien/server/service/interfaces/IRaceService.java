package at.ac.tuwien.server.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.User;

public interface IRaceService {

	
	@Transactional
	public Double getAvgSpeedInRaces(User u);
	
}
