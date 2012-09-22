package at.ac.tuwien.server.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="racelistdto")
public class RaceListDTO {

	private List<RaceDTO> raceList;

	@XmlElement(name="racedto")
	public List<RaceDTO> getRaceList() {
		return raceList;
	}

	public void setRaceList(List<RaceDTO> raceList) {
		this.raceList = raceList;
	}
	
	
}
