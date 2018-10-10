package com.astrazeneca.als.srs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.astrazeneca.als.srs.exception.SRSException;
import com.astrazeneca.als.srs.model.Location;
import com.astrazeneca.als.srs.service.LocationService;
import com.astrazeneca.als.srs.service.UserService;

@RestController
public class SeatReservationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeatReservationController.class);

	@Autowired
	private LocationService locationService;

	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	void handleDefaultMapping(HttpServletResponse response) throws IOException {
		response.sendRedirect("index.html");
	}

	@GetMapping("/csrf")
	public CsrfToken csrf(CsrfToken token) {
		return token;
	}

	@PreAuthorize("hasAuthority('RAT-Admin')")
	@PostMapping(path = "/location-register", consumes = "application/json")
	public String registerLocation(@RequestBody Location location) throws SRSException {
		LOGGER.info("RegisterLocation API");
		return locationService.registerLocation(location);
	}

	@PostMapping(path = "/location-occupy")
	public String bookSeat(@RequestBody String deskNo) throws SRSException {
		LOGGER.info("LocationOccupy API");
		return locationService.occupyLocation(deskNo);
	}

	@GetMapping(value = "/location-list")
	public List<Location> getLocationList() throws SRSException {
		LOGGER.info("LocationList API");
		return locationService.getUserLocations();
	}

	@GetMapping(value = "/user-location/{prid}")
	public String getUserLocation(@PathVariable("prid") String prid) throws SRSException {
		LOGGER.info("UserLocation API");
		return locationService.getUserLocation(prid);
	}

	@GetMapping(value = "/is-admin")
	public boolean isUserAnAdmin() throws SRSException {
		LOGGER.info("checkUserAnAdmin API");
		return userService.isAdmin();
	}

	@GetMapping(path = "/display-name")
	public String getUser() {
		LOGGER.info("GetUser API");
		return userService.getUserName();
	}
	
	@GetMapping(path = "/user-service")
	public UserService getUserService() {
		return userService;
	}

}
