package com.astrazeneca.als.srs.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.astrazeneca.als.srs.status.LocationStatus;

import lombok.Data;

@Entity
@Data
public class Location {

	@Id
	private String deskNo;
    @Column(nullable=false)
    private Double[] latLong;
    @Column(nullable=false)
    private String buildingName;
    @Column(nullable=false)
    private String floor;
	private String ownerGroup;
    private String displayName;
    private String prid;
    private String status= LocationStatus.FREE.displayName();
    private Timestamp occupiedAt;
    private Timestamp validTill;

}
