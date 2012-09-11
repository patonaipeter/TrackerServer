package at.ac.tuwien.server.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Race {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "avgSpeed")
	private Double avgSpeed;
	
	@Column(name = "distance")
	private Double distance;
	
	@Column(name = "overallElevation")
	private Double overallElevation;

	@OneToMany
	private Set<Location> locations;
	
	@ManyToMany
	private List<User> participants;
	
	@Column(name = "raceName")
	private String raceName;
	
	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Set<Location> getLocations() {
		return locations;
	}

	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	public Double getOverallElevation() {
		return overallElevation;
	}

	public void setOverallElevation(Double overallElevation) {
		this.overallElevation = overallElevation;
	}
	
	
	
}
