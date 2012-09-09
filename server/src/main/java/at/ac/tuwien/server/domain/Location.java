package at.ac.tuwien.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Location implements Comparable<Location>{

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;
	
	private Long longitude;
	private Long latitude;
	private Long altitude;
	
	private Date timestamp;
	
	@OneToOne
	private Race race;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getLongitude() {
		return longitude;
	}
	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
	public Long getLatitude() {
		return latitude;
	}
	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Race getRace() {
		return race;
	}
	public void setRace(Race race) {
		this.race = race;
	}
	public Long getAltitude() {
		return altitude;
	}
	public void setAltitude(Long altitude) {
		this.altitude = altitude;
	}
	
	@Override
	public int compareTo(Location loc){
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;
		
		if(this.getTimestamp().equals(loc.getTimestamp())) {
			return EQUAL;
		}else if(loc.getTimestamp().after(this.getTimestamp())){
			return AFTER;
		}else{
			return BEFORE;
		}
	}
	
}
