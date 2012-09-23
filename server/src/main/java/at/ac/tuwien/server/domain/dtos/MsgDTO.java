package at.ac.tuwien.server.domain.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="msgdto")
public class MsgDTO {

	private Integer id;
	private String text;
	private String sender;
	private Integer senderId;
	private Long sentDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public Long getSentDate() {
		return sentDate;
	}
	public void setSentDate(Long sentDate) {
		this.sentDate = sentDate;
	}
	
	
}
