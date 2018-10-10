package com.astrazeneca.als.srs.status;

/**
 * Created by krvh271 on 7/19/17.
 */
public enum LocationStatus {
    OCCUPIED("OCCUPIED"), FREE("FREE"), UNREGISTERED("UNREGISTERED");
    private final String status;

    LocationStatus(String status) {
        this.status = status;
    }

    public String displayName() {
        return status;
    }
}
