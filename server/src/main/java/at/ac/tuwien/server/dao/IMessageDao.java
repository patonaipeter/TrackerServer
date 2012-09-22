package at.ac.tuwien.server.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Message;
import at.ac.tuwien.server.domain.User;

public interface IMessageDao {

	@Transactional
	Message getMessageById(Integer id);
	@Transactional
	void saveMsg(Message msg);
	@Transactional
	void deleteMsg(Message msg);
	@Transactional
	List<Message> getAllMessagesForUser(User u);

}
