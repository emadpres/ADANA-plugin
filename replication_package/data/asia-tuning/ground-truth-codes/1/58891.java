SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
List<Sensor> s=sensorManager.getSensorList(Sensor.TYPE_ALL);
 for (int i=0;i<s.size();i++)
   {
        Sensor tmp = s.get(i);
        if (tmp.getType() == Sensor.TYPE_ACCELEROMETER)
        DEVICE_HAS_ACCELEROMETER=true;
         else
        DEVICE_HAS_ACCELEROMETER=false;

 };
