package at.ac.tuwien.server.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.server.domain.Message;
import at.ac.tuwien.server.domain.User;

@Repository
public class MessageDaoImpl implements IMessageDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Message getMessageById(Integer id) {
		return (Message) sessionFactory.getCurrentSession().get(Message.class, id);
	}

	@Override
	@Transactional
	public void saveMsg(Message msg) {
		sessionFactory.getCurrentSession().saveOrUpdate(msg);
		
	}

	@Override
	@Transactional
	public void deleteMsg(Message msg) {
		sessionFactory.getCurrentSession().delete(msg);
	}

	@Override
	@Transactional
	public List<Message> getAllMessagesForUser(User u) {
		Query q = sessionFactory.getCurrentSession().createQuery("select m from Message m where m.receiver.id =:userid");
		q.setParameter("userid", u.getId());
		List<Message> list = (List<Message>) q.list();
		return  list;
	}
	
	

}
