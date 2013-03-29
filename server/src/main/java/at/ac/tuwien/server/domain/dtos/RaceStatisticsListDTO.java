package at.ac.tuwien.server.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="racestatisticslistdto")
public class RaceStatisticsListDTO {

	private Integer raceId;
	private String raceName;
	private List<RaceStatisticsDTO> stats;
	
	
	
	public RaceStatisticsListDTO() {
	}
	public Integer getRaceId() {
		return raceId;
	}
	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	@XmlElement(name="racestatisticsdto")
	public List<RaceStatisticsDTO> getStats() {
		return stats;
	}
	public void setStats(List<RaceStatisticsDTO> stats) {
		this.stats = stats;
	}
	
}
