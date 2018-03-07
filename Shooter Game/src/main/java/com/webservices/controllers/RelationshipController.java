package com.webservices.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webservices.model.Address;
import com.webservices.model.Opponents;
import com.webservices.model.Player;
import com.webservices.model.Sponsor;
import com.webservices.service.OpponentService;
import com.webservices.service.PlayerService;
import com.webservices.service.SponsorService;

@Controller
public class RelationshipController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private OpponentService opponentService;

	/* Create Player API */

	/**
	 * This Api creates Player in database.
	 * 
	 * @param model
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param description
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param country
	 * @param street
	 * @param sponsor
	 * @param opponents
	 * @return ok response when player is successfully created.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/player", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> createPlayer(Model model, String firstName, String lastName, String email,
			String description, String city, String state, String zipcode, String country, String street,
			Integer sponsor, String opponents) {

		String result = "";
		if (opponents != null) {
			return new ResponseEntity("Opponent must not be passed as parameter.", HttpStatus.BAD_REQUEST);
		}
		if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty() && email != null
				&& !email.isEmpty()) {
			try {
				System.out.println("inside create player");
				Address address = new Address();
				address.setStreet(street);
				address.setCity(city);
				address.setCountry(country);
				address.setZipcode(zipcode);
				address.setState(state);

				Player player = new Player();
				player.setDescription(description);
				player.setEmail(email);
				player.setFirstname(firstName);
				player.setLastname(lastName);
				player.setAddress(address);
				if (sponsor != null) {
					Sponsor sponsorObj = sponsorService.getSponsor(sponsor);
					if (sponsorObj != null) {
						player.setSponsor(sponsorObj);
					} else {
						return new ResponseEntity("Sponsor is not present", HttpStatus.BAD_REQUEST);
					}
				}

				playerService.add(player);
				return new ResponseEntity(player, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
			}
		} else {
			result = "Please enter required fields like firstname lastname and email";
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - Get Player API - - - - */

	/**
	 * This Api gets Player from database according to the id given in url.
	 * 
	 * @param model
	 * @param id
	 * @return Player Json, if player is present in database.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/player/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getPlayer(Model model, @PathVariable("id") int id) {
		String result = "";
		try {

			System.out.println("inside get player");
			Player player = playerService.getPlayer(id);
			if (player != null) {

				List<Integer> opponents = opponentService.getAllOpponents(player.getId());
				if (!(opponents == null)) {
					player.setOpponents(opponents);
				}

				return new ResponseEntity(player, HttpStatus.OK);

			} else {
				result = "Player does not exist.";
				return new ResponseEntity(result, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - Update Player API - - - - */

	/**
	 * This Api update Player in database according to the id given in url.
	 * 
	 * @param model
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param description
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param country
	 * @param street
	 * @param sponsorId
	 * @returns updated player after successful update.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/player/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> updatePerson(Model model, @PathVariable Integer id, String firstName, String lastName,
			String email, String description, String city, String state, String zipcode, String country, String street,
			Integer sponsorId) {

		String result = "";
		if (email != null && !email.isEmpty() && id != null) {
			try {
				System.out.println("inside update player");
				Player player = playerService.getPlayer(id);
				if (player != null) {

					if (sponsorId != null) {

						Sponsor sponsor = sponsorService.getSponsor(sponsorId);
						player.setSponsor(sponsor != null ? sponsor : null);
					}

					Address address = new Address();
					address.setStreet(street);
					address.setCity(city);
					address.setCountry(country);
					address.setZipcode(zipcode);
					address.setState(state);

					player.setDescription(description);
					player.setEmail(email);
					player.setFirstname(firstName);
					player.setLastname(lastName);
					player.setAddress(address);

					playerService.edit(player);
					return new ResponseEntity(player, HttpStatus.OK);
				} else {
					result = "Player does not exist.";
					return new ResponseEntity(result, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
			}
		} else {
			result = "Please provide required fields.";
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - Delete Person API - - - - */

	/**
	 * This Api Deletes player in database according to the id given in url
	 * param.
	 * 
	 * @param model
	 * @param id
	 * @returns deleted player in Json after successful delete.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePerson(Model model, @PathVariable Integer id) {
		String result = "";
		if (id != null) {

			try {
				System.out.println("inside delete player");

				Player player = playerService.getPlayer(id);
				if (player != null) {

					List<Integer> opponentList = opponentService.getAllOpponents(player.getId());
					if (opponentList != null) {
						for (Integer opponentId : opponentList) {
							deleteOpponent(model, player.getId(), opponentId);
						}
						player.setOpponents(opponentList);
					}
					playerService.delete(id);
					return new ResponseEntity(player, HttpStatus.OK);
				} else {
					result = "Player does not exist.";
					return new ResponseEntity(result, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
			}

		} else {
			result = "Please provide required fields.";
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - create sponsor API - - - - */

	/**
	 * This Api creates Sponsor in database.
	 * 
	 * @param model
	 * @param name
	 * @param email
	 * @param description
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param country
	 * @param street
	 * @returns sponsor in json after successful creation.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sponsor", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createSponsor(Model model, String name, String email, String description, String city,
			String state, String zipcode, String country, String street) {

		String result = "";
		if (name != null && !name.isEmpty()) {

			try {

				Address address = new Address();
				address.setStreet(street);
				address.setCity(city);
				address.setCountry(country);
				address.setZipcode(zipcode);
				address.setState(state);

				Sponsor sponsor = new Sponsor();
				sponsor.setDescription(description);
				sponsor.setEmail(email);
				sponsor.setName(name);
				sponsor.setAddress(address);

				sponsorService.add(sponsor);
				return new ResponseEntity(sponsor, HttpStatus.OK);

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity("Exception Occured", HttpStatus.BAD_REQUEST);
			}
		} else {
			result = "Please provide required fields.";
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - Get Sponsor API - - - - */

	/**
	 * This Api gets sponsor from databse according to the id given in url
	 * param.
	 * 
	 * @param model
	 * @param id
	 * @return sponsor Json if sponsor is present in database.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sponsor/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getSponsor(Model model, @PathVariable Integer id) {
		String result = "";
		try {
			System.out.println("inside get Sponsor");
			if (id != null) {

				Sponsor sponsor = sponsorService.getSponsor(id);

				if (sponsor != null) {
					return new ResponseEntity(sponsor, HttpStatus.OK);
				} else {
					result = "Sponsor does not exist.";
					return new ResponseEntity(result, HttpStatus.NOT_FOUND);
				}
			} else {
				result = "Please enter required fields.";
				return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
		}
	}

	/* Update Sponsor API */

	/**
	 * This Api Update sponsor in databse according to the id given in url
	 * param.
	 * 
	 * @param id
	 * @param model
	 * @param name
	 * @param email
	 * @param description
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param country
	 * @param street
	 * @return return updated sponsor in Json if update is successful.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sponsor/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> updateSponsor(@PathVariable int id, Model model, String name, String email,
			String description, String city, String state, String zipcode, String country, String street) {

		String result = "";

		if (name != null && !name.isEmpty()) {
			try {
				Sponsor sponsor = sponsorService.getSponsor(id);

				if (sponsor != null) {

					Address address = new Address();
					address.setStreet(street);
					address.setCity(city);
					address.setCountry(country);
					address.setZipcode(zipcode);
					address.setState(state);

					sponsor.setDescription(description);
					sponsor.setEmail(email);
					sponsor.setName(name);
					sponsor.setAddress(address);

					sponsorService.edit(sponsor);
					return new ResponseEntity(sponsor, HttpStatus.OK);
				} else {
					result = "Sponsor does not exist.";
					return new ResponseEntity(result, HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
			}
		} else {
			result = "Please provide required fields.";
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - Delete Sponsor API - - - - */

	/**
	 * This Api is use to delete sponsor from database according to the id given
	 * in url param.
	 * 
	 * @param model
	 * @param id
	 * @returns deleted sponsor Json after successful deletion
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/sponsor/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSponsor(Model model, @PathVariable int id) {
		String result = "";
		try {
			System.out.println("inside delete sponsor");
			Sponsor sponsor = sponsorService.getSponsor(id);
			if (sponsor != null) {

				List<Player> listPlayer = playerService.getPlayerList(sponsor);
				if (listPlayer != null) {
					result = "Can not delete this Sponsor. Atleast one Player belongs to this Sponsor";
					return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
				} else {
					sponsorService.delete(id);
					return new ResponseEntity(sponsor, HttpStatus.OK);
				}
			} else {
				result = "Sponsor Does not exist.";
				return new ResponseEntity(result, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - Create Opponents API - - - - */

	/**
	 * This Api is used to create Opponent in Database.
	 * 
	 * @param model
	 * @param id1
	 * @param id2
	 * @returns Ok response after successful creation.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/opponents/{id1}/{id2}", method = RequestMethod.PUT)
	public ResponseEntity<?> createOpponent(Model model, @PathVariable Integer id1, @PathVariable Integer id2) {

		String result = "";
		if (id1 != null && id2 != null && id1 != id2) {
			try {
				Player p1 = playerService.getPlayer(id1);
				Player p2 = playerService.getPlayer(id2);
				if (p1 != null && p2 != null) {
					Opponents f = opponentService.search(id1, id2);
					if (f == null) {
						f = new Opponents();
						f.setPlayer1(p1);
						f.setPlayer2(p2);
						opponentService.add(f);
						return new ResponseEntity("Opponents created", HttpStatus.OK);
					} else {
						result = "Players are already Opponents.";
						return new ResponseEntity(result, HttpStatus.OK);
					}
				} else {
					result = "Player(/s) does not exist.";
					return new ResponseEntity(result, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
			}
		} else {
			result = "Please provide valid inputs.";
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}

	/* - - - - Delete Opponent API - - - - */

	/**
	 * This Api is use to Delete Opponent from databse according to the id given
	 * in url param.
	 * 
	 * @param model
	 * @param id1
	 * @param id2
	 * @return ok response after successful deletion.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/opponents/{id1}/{id2}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOpponent(Model model, @PathVariable Integer id1, @PathVariable Integer id2) {
		String result = "";
		if (id1 != null && id2 != null && id1 != id2) {
			try {

				Player p1 = playerService.getPlayer(id1);
				Player p2 = playerService.getPlayer(id2);

				if (p1 != null && p2 != null) {

					Opponents f = opponentService.search(id1, id2);
					if (f != null) {
						opponentService.delete(f);
						return new ResponseEntity("Successfully removed Opponents ", HttpStatus.OK);
					} else {
						result = "Players are not opponents";
						return new ResponseEntity(result, HttpStatus.NOT_FOUND);
					}
				} else {
					result = "Players not found";
					return new ResponseEntity(result, HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity("Invalid Request", HttpStatus.BAD_REQUEST);
			}
		} else {
			result = "Please provide valid inputs.";
			return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}
	}
}
