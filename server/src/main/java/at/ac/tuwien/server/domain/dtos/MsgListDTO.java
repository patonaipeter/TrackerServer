package at.ac.tuwien.server.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="msglistdto")
public class MsgListDTO {

	private List<MsgDTO> msgList;
	public MsgListDTO() {
		
	}

	public MsgListDTO(List<MsgDTO> msgList) {
		super();
		this.msgList = msgList;
	}


	@XmlElement(name="msgdto")
	public List<MsgDTO> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<MsgDTO> msgList) {
		this.msgList = msgList;
	}
	
	
}
