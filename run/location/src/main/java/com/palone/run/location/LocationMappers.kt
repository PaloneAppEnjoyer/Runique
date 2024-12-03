package com.palone.run.location

import android.location.Location
import com.palone.core.domain.location.LocationWithAltitude


fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        altitude = altitude,
        location = com.palone.core.domain.location.Location(latitude, longitude)
    )
}