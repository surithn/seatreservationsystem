package com.astrazeneca.als.srs.service.impl;

import com.astrazeneca.als.srs.model.Location;
import com.astrazeneca.als.srs.repository.LocationRepository;
import com.astrazeneca.als.srs.service.LocationService;
import com.astrazeneca.als.srs.service.SchedulerService;
import com.astrazeneca.als.srs.status.LocationStatus;
import com.astrazeneca.als.srs.util.SeatReservationUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by krvh271 on 7/19/17.
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    LocationService locationService;

    @Autowired
    LocationRepository locationRepository;

    @Override
    public String monitorAvailability() {
        List<Location> locationList=locationService.getAllLocation();

        List<Location> result = locationList.stream()
                .filter(location -> System.currentTimeMillis()>(location.getValidTill()!=null?location.getValidTill().getTime():0))
                .map(location -> makeLocationAvailable(location))
                .collect(Collectors.toList());

        locationRepository.save(result);
        return SeatReservationUtil.SEATS_AVAILABILITY_REVISITED;
    }

    private Location makeLocationAvailable(Location location){
        location.setValidTill(null);
        location.setOccupiedAt(null);
        location.setPrid(SeatReservationUtil.EMPTY);
        location.setStatus(LocationStatus.FREE.displayName());
        location.setDisplayName(SeatReservationUtil.EMPTY);
        return location;
    }
}
