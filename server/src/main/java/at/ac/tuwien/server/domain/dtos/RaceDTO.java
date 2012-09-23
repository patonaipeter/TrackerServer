package at.ac.tuwien.server.domain.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="racedto")
public class RaceDTO {

	private Integer id;
	private String name;
	private Long date;
	private Double length;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	
	
}
