package com.webservices.dao;

import java.util.List;

import com.webservices.model.Player;
import com.webservices.model.Sponsor;

public interface PlayerDao {
	public void add(Player player);
	public Player getPlayer(int id);
	public void edit(Player person);
	public void delete(int id);
	public List<Player> getPlayerList(Sponsor sponsor);
}
