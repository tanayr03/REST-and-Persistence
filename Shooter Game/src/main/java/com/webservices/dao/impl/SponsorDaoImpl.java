package com.webservices.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.webservices.dao.SponsorDao;
import com.webservices.model.Sponsor;

@Repository
public class SponsorDaoImpl implements SponsorDao {
	@Autowired
	private SessionFactory session;

	public Sponsor getSponsor(int id) {
		return (Sponsor) session.getCurrentSession().get(Sponsor.class, id);
	}

	public void add(Sponsor sponsor) {
		session.getCurrentSession().save(sponsor);
	}

	public void edit(Sponsor sponsor) {
		session.getCurrentSession().update(sponsor);
	}

	public void delete(int id) {
		Query query = session.getCurrentSession().createQuery("delete Sponsor where sponsorId = :ID");
		query.setParameter("ID", id);
		query.executeUpdate();
	}

}
