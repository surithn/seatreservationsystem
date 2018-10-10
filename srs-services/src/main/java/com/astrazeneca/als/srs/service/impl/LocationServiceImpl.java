package com.astrazeneca.als.srs.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.astrazeneca.als.srs.model.Location;
import com.astrazeneca.als.srs.repository.LocationRepository;
import com.astrazeneca.als.srs.service.LocationService;
import com.astrazeneca.als.srs.service.UserService;
import com.astrazeneca.als.srs.status.LocationStatus;
import com.astrazeneca.als.srs.util.SeatReservationUtil;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
    public static final long MAX_TIME = 32400000;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserService userService;

    @Override
    public String registerLocation(Location location) {
        try {
            locationRepository.save(location);
            return SeatReservationUtil.REGISTERED_SUCCESS;
        } catch (DataIntegrityViolationException e){
            LOGGER.error(e.getMessage());
            return SeatReservationUtil.DETAILS_MANDATORY;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return SeatReservationUtil.REGISTERED_FAILED;
        }
    }

    @Override
    public String occupyLocation(String deskNo) {
        try {
            Location userLocation = locationRepository.findLocationByPrid(userService.getUserPrid());
            if (userLocation != null) {
                return SeatReservationUtil.ALREADY_OCCUPIED + userLocation.getDeskNo();
            }
            return recheckAvailability(deskNo);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return SeatReservationUtil.OCCUPIED_FAILED;
        }
    }

    private synchronized String recheckAvailability(String deskNo) {
        Location location=locationRepository.findOne(deskNo);
        if(location.getStatus().equals(LocationStatus.FREE.displayName())){
            updateLocation(location);
            locationRepository.save(location);
            return SeatReservationUtil.OCCUPIED_SUCCESS;
        } else {
            return SeatReservationUtil.JUST_OCCUPIED;
        }
    }

    private boolean isSeatAvailable(String deskNo) {
        return locationRepository.findOne(deskNo).getStatus().equals(LocationStatus.FREE.displayName());
    }

    private void updateLocation(Location location) {
        location.setDisplayName(userService.getUserName());
        location.setPrid(userService.getUserPrid());
        location.setStatus(LocationStatus.OCCUPIED.displayName());
        location.setOccupiedAt(new Timestamp(System.currentTimeMillis()));
        location.setValidTill(new Timestamp(System.currentTimeMillis() + MAX_TIME));
    }

    @Override
    public List<Location> getUserLocations() {
        return locationRepository.findAll();
    }

    @Override
    public String getUserLocation(String prid) {
        Location userLocation=locationRepository.findLocationByPrid(prid);
        return userLocation!=null?userLocation.getDeskNo():SeatReservationUtil.NOT_OCCUPIED;
    }

    @Override
    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

}
