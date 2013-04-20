package at.ac.tuwien.server.domain.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="locationdto")
public class LocationDTO {

	private Integer id;
	private Long date;
	private Double longitude;
	private Double latitude;
	private Double altitude;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
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
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	
	
	
}
