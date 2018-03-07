package com.webservices.service;

import com.webservices.model.Sponsor;

public interface SponsorService {
	public Sponsor getSponsor(int id);

	public void add(Sponsor sponsor);

	public void edit(Sponsor sponsor);

	public void delete(int id);
}
