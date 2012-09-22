package at.ac.tuwien.server.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="userlistdto")
public class UserListDTO {

	private List<UserDTO> userList;
	
	public UserListDTO() {
	}

	public UserListDTO(List<UserDTO> userList) {
		super();
		this.userList = userList;
	}

	@XmlElement(name="userdto")
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
	
	
	
}
