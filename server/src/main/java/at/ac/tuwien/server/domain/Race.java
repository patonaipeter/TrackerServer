package at.ac.tuwien.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	//Todo add date connection to user and location
	
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
	
	
	
}
