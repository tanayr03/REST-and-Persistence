package com.webservices.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webservices.dao.SponsorDao;
import com.webservices.model.Sponsor;
import com.webservices.service.SponsorService;
@Service
public class SponsorServiceImpl implements SponsorService{
	@Autowired
	private SponsorDao sponsorDao;
	

	
	@Transactional
	public Sponsor getSponsor(int id) {
		return sponsorDao.getSponsor(id);
	}
	
	@Transactional
	public void add(Sponsor sponsor) {
		sponsorDao.add(sponsor);
	}


	@Transactional
	public void edit(Sponsor sponsor) {
		sponsorDao.edit(sponsor);
	}

	@Transactional
	public void delete(int id) {
		sponsorDao.delete(id);
	}

}
