package at.ac.tuwien.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.ac.tuwien.server.dao.interfaces.IRaceDao;
import at.ac.tuwien.server.dao.interfaces.IRaceStatisticsDao;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.RaceStatistics;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.RaceStatisticsDTO;
import at.ac.tuwien.server.domain.dtos.RaceStatisticsListDTO;
import at.ac.tuwien.server.service.interfaces.IRaceService;
import at.ac.tuwien.server.service.interfaces.IUserService;

@Controller
public class RaceController {

	@Autowired
	IUserService userService;
	@Autowired
	IRaceService raceService;
	@Autowired
	IRaceStatisticsDao raceStatisticsDao;
	@Autowired
	IRaceDao raceDao;
	
	@Transactional
	@RequestMapping(value="updateracestatus", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody RaceStatisticsListDTO updateRaceStatus(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User user = userService.getUser(username, password);
		Integer raceId = Integer.parseInt(requestData.getFirst("raceid"));
		Race race = raceDao.getRaceById(raceId);
		List<RaceStatistics> stats = raceStatisticsDao.retrieveRaceStatisticsForRace(raceId);
		
		List<RaceStatisticsDTO> dtos = new ArrayList<RaceStatisticsDTO>();
		for(RaceStatistics r : stats){
			RaceStatisticsDTO dto = new RaceStatisticsDTO();
			dto.setAvgSpeed(r.getAvgSpeed());
			dto.setDistance(r.getDistance());
			dto.setUserid(user.getId());
			dto.setUsername(user.getUsername());
			dtos.add(dto);
		}
		RaceStatisticsListDTO liste = new RaceStatisticsListDTO();
		liste.setStats(dtos);
		liste.setRaceId(raceId);
		liste.setRaceName(race.getRaceName());
		return liste;
		
	}
	
	
}
