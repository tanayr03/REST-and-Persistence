package com.webservices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webservices.dao.PlayerDao;
import com.webservices.model.Player;
import com.webservices.model.Sponsor;
import com.webservices.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerDao playerDao;

	@Transactional
	public void add(Player player) {
		 playerDao.add(player);
	}

	@Transactional
	public Player getPlayer(int id) {
		return playerDao.getPlayer(id);
	}

	@Transactional
	public void edit(Player player) {
		playerDao.edit(player);		
	}
	
	@Transactional
	public void delete(int id) {
		playerDao.delete(id);
	}


	
	@Transactional
	public List<Player> getPlayerList(Sponsor sponsor) {
		return playerDao.getPlayerList(sponsor);
	}
}
