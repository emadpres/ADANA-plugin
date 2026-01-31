double radiusDegrees = 1.0;
LatLng center = /* the user's location */;
LatLng northEast = new LatLng(center.latitude + radiusDegrees, center.longitude + radiusDegrees);
LatLng southWest = new LatLng(center.latitude - radiusDegrees, center.longitude - radiusDegrees);
LatLngBounds bounds = LatLngBounds.builder().include(northEast).include(southWest).build();;
