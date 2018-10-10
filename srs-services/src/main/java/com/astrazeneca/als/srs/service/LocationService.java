package com.astrazeneca.als.srs.service;

import com.astrazeneca.als.srs.model.Location;
import java.util.List;

/**
 * Created by krvh271 on 7/19/17.
 */

public interface LocationService {
    String registerLocation(Location location);

    String occupyLocation(String deskNo);

    List<Location> getUserLocations();

    String getUserLocation(String prid);

    List<Location> getAllLocation();
}
