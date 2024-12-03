package com.palone.run.domain

import com.palone.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observerLocation(interval: Long): Flow<LocationWithAltitude>
}