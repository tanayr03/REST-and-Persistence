package com.webservices.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webservices.dao.PlayerDao;
import com.webservices.model.Player;
import com.webservices.model.Sponsor;

@Repository
public class PlayerDaoImpl implements PlayerDao {

	@Autowired
	private SessionFactory session;

	public void add(Player player) {
		session.getCurrentSession().save(player);
	}

	public Player getPlayer(int id) {
		return (Player) session.getCurrentSession().get(Player.class, id);
	}

	public void edit(Player person) {
		session.getCurrentSession().update(person);

	}

	public void delete(int id) {
		System.out.println(" id " + id);
		Query query = session.getCurrentSession().createQuery("delete Player where id = :ID");

		query.setParameter("ID", id);

		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Player> getPlayerList(Sponsor sponsor) {
		System.out.println(" sponsor id " + sponsor.getId());
		Criteria c = session.getCurrentSession().createCriteria(Player.class, "p");
		c.add(Restrictions.eq("p.sponsor.id", sponsor.getId()));
		List<Player> listPlayer = (ArrayList<Player>) c.list();
		if (!listPlayer.isEmpty() && listPlayer.size() > 0) {
			return listPlayer;
		} else {
			return null;
		}
	}
}
