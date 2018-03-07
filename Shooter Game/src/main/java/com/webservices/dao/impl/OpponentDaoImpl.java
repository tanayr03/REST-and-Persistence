package com.webservices.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.webservices.dao.OpponentDao;
import com.webservices.model.Opponents;

public class OpponentDaoImpl implements OpponentDao {
	@Autowired
	private SessionFactory session;

	@SuppressWarnings("unchecked")
	public List<Integer> getAllOpponents(int id) {
		Criteria c = session.getCurrentSession().createCriteria(Opponents.class, "f");
		c.add(Restrictions.eq("f.player1.id", id));
		if (!c.list().isEmpty() && c.list().size() > 0) {
			List<Opponents> listOpponents = (ArrayList<Opponents>) c.list();
			if (!listOpponents.isEmpty()) {
				List<Integer> opponentList = new ArrayList<Integer>();
				for (Opponents opponent : listOpponents) {
					opponentList.add(opponent.getPlayer2().getId());
				}
				return opponentList;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Opponents search(Integer id1, Integer id2) {
		Criteria c = session.getCurrentSession().createCriteria(Opponents.class, "o");
		c.add(Restrictions.eq("o.player1.id", id1));
		c.add(Restrictions.eq("o.player2.id", id2));
		List<Opponents> listOpponents = c.list();
		if (listOpponents != null && listOpponents.size() > 0) {
			return listOpponents.get(0);
		}
		return null;
	}

	public void delete(Opponents opponents) {
		Query query = session.getCurrentSession().createQuery("delete Opponents where id = :ID");
		query.setParameter("ID", opponents.getId());
		query.executeUpdate();
	}

	public void add(Opponents opponents) {
			session.getCurrentSession().save(opponents);
		
	}
}
