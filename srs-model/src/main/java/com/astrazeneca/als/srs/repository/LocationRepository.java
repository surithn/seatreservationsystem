package com.astrazeneca.als.srs.repository;

import com.astrazeneca.als.srs.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Location findLocationByPrid(String prid);
}