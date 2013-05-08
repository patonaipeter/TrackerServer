
package at.ac.tuwien.server.domain.dtos;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="statisticsdto")
public class StatisticsDTO {
	private String name;
	private Double distance;
	private Double elevation;
	private Double avgSpeed;
	private Integer numberOfRaces;
	private Double distanceInRaceMode;
	private Double avgSpeedInRaceMode;
	private Integer raceWins;
	private Integer score;
	
	public StatisticsDTO() { }
	
	public StatisticsDTO(String name, Double distance, Double elevation, Double avgSpeed, Integer numberOfRaces, Double distanceInRaceMode, Double avgSpeedInRaceMode, Integer raceWins, Integer score) {
		this.name = name;
		this.distance = distance;
		this.elevation = elevation;
		this.avgSpeed = avgSpeed;
		this.numberOfRaces = numberOfRaces;
		this.distanceInRaceMode = distanceInRaceMode;
		this.avgSpeedInRaceMode = avgSpeedInRaceMode;
		this.raceWins = raceWins;
		this.score = score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getElevation() {
		return elevation;
	}

	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}

	public Double getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(Double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public Integer getNumberOfRaces() {
		return numberOfRaces;
	}

	public void setNumberOfRaces(Integer numberOfRaces) {
		this.numberOfRaces = numberOfRaces;
	}

	public Double getDistanceInRaceMode() {
		return distanceInRaceMode;
	}

	public void setDistanceInRaceMode(Double distanceInRaceMode) {
		this.distanceInRaceMode = distanceInRaceMode;
	}

	public Double getAvgSpeedInRaceMode() {
		return avgSpeedInRaceMode;
	}

	public void setAvgSpeedInRaceMode(Double avgSpeedInRaceMode) {
		this.avgSpeedInRaceMode = avgSpeedInRaceMode;
	}

	public Integer getRaceWins() {
		return raceWins;
	}

	public void setRaceWins(Integer raceWins) {
		this.raceWins = raceWins;
	}

	public void setScore(Integer score) {
		this.score = score;
		
	}
	public Integer getScore() {
		return score;
	}

	
	
}
