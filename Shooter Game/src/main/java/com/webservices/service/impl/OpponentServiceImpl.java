package com.webservices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.webservices.dao.OpponentDao;
import com.webservices.model.Opponents;
import com.webservices.service.OpponentService;

public class OpponentServiceImpl implements OpponentService{
	@Autowired 
	OpponentDao opponentDao; 


	@Transactional
	public List<Integer> getAllOpponents(int id) {
		return opponentDao.getAllOpponents(id);
	}
	
	@Transactional
	public Opponents search(Integer id1, Integer id2) {
		return opponentDao.search(id1, id2);
	}

	@Transactional
	public void delete(Opponents opponents) {
		opponentDao.delete(opponents);		
	}

	@Transactional
	public void add(Opponents opponents) {
		opponentDao.add(opponents);
		
	}
}
