package at.ac.tuwien.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import at.ac.tuwien.server.domain.Location;
import at.ac.tuwien.server.domain.Race;
import at.ac.tuwien.server.domain.RaceStatistics;
import at.ac.tuwien.server.domain.User;
import at.ac.tuwien.server.domain.dtos.LocationDTO;
import at.ac.tuwien.server.domain.dtos.LocationListDTO;
import at.ac.tuwien.server.domain.dtos.RaceDTO;
import at.ac.tuwien.server.domain.dtos.RaceListDTO;
import at.ac.tuwien.server.domain.dtos.RaceStatisticsDTO;
import at.ac.tuwien.server.domain.dtos.RaceStatisticsListDTO;
import at.ac.tuwien.server.domain.dtos.UserDTO;
import at.ac.tuwien.server.domain.dtos.UserListDTO;
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
			dto.setUserid(r.getUser().getId());
			dto.setUsername(r.getUser().getUsername());
			dtos.add(dto);
		}
		RaceStatisticsListDTO liste = new RaceStatisticsListDTO();
		liste.setStats(dtos);
		liste.setRaceId(raceId);
		liste.setRaceName(race.getRaceName());
		return liste;
		
	}
	
	
	@Transactional
	@RequestMapping(value="browseraces", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody RaceListDTO browseRaces(@RequestBody LinkedMultiValueMap<String, String> requestData) {
	
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User user = userService.getUser(username, password);
		
		//get races in that user is involved
		Set<Race> races = user.getRaces();
		RaceListDTO raceListDto = new RaceListDTO();
		List<RaceDTO> raceDTOS = new ArrayList<RaceDTO>();
		for(Race race : races){
			RaceDTO dto = new RaceDTO();
			//TODO save Race Date in the DB
			dto.setDate(new Date().getTime());
			dto.setId(race.getId());
			dto.setLength(race.getDistance());
			dto.setName(race.getRaceName());
			raceDTOS.add(dto);
		}
		raceListDto.setRaceList(raceDTOS);
		
		return  raceListDto;
	}
	
	@Transactional
	@RequestMapping(value="browseparticipants", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody UserListDTO browseParticipants(@RequestBody LinkedMultiValueMap<String, String> requestData) {
		
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User user = userService.getUser(username, password);
		Integer raceId = Integer.parseInt(requestData.getFirst("raceid"));
		Race race = raceDao.getRaceById(raceId);
		
		List<User> users = race.getParticipants();
		UserListDTO userListDTO = new UserListDTO();
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for(User u : users){
			UserDTO dto = new UserDTO();
			
			dto.setEmail(u.getEmail());
			dto.setId(u.getId());
			dto.setLast_activity_date(u.getLast_activity_date().getTime());
			dto.setNumOfFriends(u.getFirends().size());
			dto.setRegister_date(u.getRegister_date().getTime());
			dto.setScore(u.getScore());
			dto.setUsername(u.getUsername());
			
			dtos.add(dto);
		}
		userListDTO.setUserList(dtos);
	
		return userListDTO;
	}
	
	@Transactional
	@RequestMapping(value="getracepoints", method=RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody LocationListDTO getRacePoints(@RequestBody LinkedMultiValueMap<String, String> requestData) {
	
		String username = requestData.getFirst("username");
		String password = requestData.getFirst("password");
		User user = userService.getUser(username, password);
		Integer raceId = Integer.parseInt(requestData.getFirst("raceid"));
		Race race = raceDao.getRaceById(raceId);
		Set<Location> locs = race.getLocations();
		
		LocationListDTO locListDTO = new LocationListDTO();
		List<LocationDTO> dtos = new ArrayList<LocationDTO>();
		for(Location l : locs){
			LocationDTO dto = new LocationDTO();
			dto.setAltitude(l.getAltitude());
			dto.setDate(l.getTimestamp().getTime());
			dto.setId(l.getId());
			dto.setLatitude(l.getLatitude());
			dto.setLongitude(l.getLongitude());
			dtos.add(dto);
		}
		locListDTO.setLocationList(dtos);
		return locListDTO;
		
	}
}
