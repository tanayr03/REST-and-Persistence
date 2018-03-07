package com.webservices.service;

import java.util.List;

import com.webservices.model.Opponents;

public interface OpponentService {
	public List<Integer> getAllOpponents(int id);
	public Opponents search(Integer id1, Integer id2);
	public void delete(Opponents opponents);
	public void add(Opponents opponents);
	
}
