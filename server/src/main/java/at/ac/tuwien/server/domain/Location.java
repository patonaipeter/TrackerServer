package at.ac.tuwien.server.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"altitude", "latitude", "timestamp", "longitude", "race_id", "user_id"})})
public class Location implements Comparable<Location>{

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Integer id;
	
	private Double longitude;
	private Double latitude;
	private Double altitude;
	
	private Date timestamp;
	@ManyToOne
	private User user;
	
	@OneToOne
	private Race race;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
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
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
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
	
	

	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
