package at.ac.tuwien.server.domain.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="userdto")
public class UserDTO {


	private Integer id;

	private String username;

	private String email;
	
	private Integer score;

	private Long register_date;
	private Long last_activity_date;
	
	private Integer numOfFriends;
	
	private Double lastLongitude;
	private Double lastLatitude;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Long register_date) {
		this.register_date = register_date;
	}

	public Long getLast_activity_date() {
		return last_activity_date;
	}

	public void setLast_activity_date(Long last_activity_date) {
		this.last_activity_date = last_activity_date;
	}

	public Integer getNumOfFriends() {
		return numOfFriends;
	}

	public void setNumOfFriends(Integer numOfFriends) {
		this.numOfFriends = numOfFriends;
	}

	public Double getLastLongitude() {
		return lastLongitude;
	}

	public void setLastLongitude(Double lastLongitude) {
		this.lastLongitude = lastLongitude;
	}

	public Double getLastLatitude() {
		return lastLatitude;
	}

	public void setLastLatitude(Double lastLatitude) {
		this.lastLatitude = lastLatitude;
	}


	
}
