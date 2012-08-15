package at.ac.tuwien.server.domain;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Race {

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "avgSpeed")
	private Long avgSpeed;
	
	@Column(name = "distance")
	private Long distance;

	@OneToMany
	private Set<Location> locations;
	
	@ManyToMany
	private List<User> participants;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(Long avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
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
	
	
	
}
