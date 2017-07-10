package com.example.a1.rest_client.Model;

/**
 * Created by a1 on 2017. 7. 3..
 */

public class WeatherModel {
    Double temp;
    Integer pressure;
    Integer humidity;
    Double temp_min;
    Double temp_max;


    public Double getTemp() {
        return temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

}
