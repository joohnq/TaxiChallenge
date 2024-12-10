package com.joohhq.taxichallenge.data.mapper

import com.google.android.gms.maps.model.LatLng
import com.joohhq.taxichallenge.entities.Location

fun Location.toLatLng(): LatLng = LatLng(latitude, longitude)
