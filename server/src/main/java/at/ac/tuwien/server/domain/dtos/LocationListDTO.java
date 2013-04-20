package at.ac.tuwien.server.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="locationlistdto")
public class LocationListDTO {

	private List<LocationDTO> locationList;

	@XmlElement(name="locationdto")
	public List<LocationDTO> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<LocationDTO> locationList) {
		this.locationList = locationList;
	}
	
	
}
