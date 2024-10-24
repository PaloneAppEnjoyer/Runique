package com.palone.core.domain.location

import kotlin.time.Duration

data class LocationTimestamp(val locationWithAltitude: LocationWithAltitude, val duration: Duration)
