package at.ac.tuwien.server.domain.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="racestatisticsdto")
public class RaceStatisticsDTO {

	private Integer userid;
	private String username;
	private Double avgSpeed;
	private Double distance;
	
	public RaceStatisticsDTO() {
	}
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getAvgSpeed() {
		return avgSpeed;
	}
	public void setAvgSpeed(Double avgSpeed) {
		this.avgSpeed = avgSpeed;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	
	
}
