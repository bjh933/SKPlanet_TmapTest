package com.example.a1.hanium_rest_client.Model;

/**
 * Created by a1 on 2017. 7. 10..
 */

public class wModel {

        public enum SkyStatus {sunny, cloudy, rainy, snowy, foggy}
        public enum WeatherAmount {none, light, medium, heavy}
        public enum WindDirection {N, S, W , E, NW, SW, SN , NE}

        private double longitude = 0.0;
        private double latitude = 0.0;
        private SkyStatus skyStatus = SkyStatus.sunny;
        private WeatherAmount rainAmount = WeatherAmount.none;
        private WeatherAmount snowAmount = WeatherAmount.none;
        private WeatherAmount fogAmount = WeatherAmount.none;
        private WeatherAmount cloudAmount = WeatherAmount.none;
        private WindDirection windDirection = WindDirection.N;
        private double windSpeed = 0.0;


        public double getLongitude() {
            return longitude;
        }
        public void setLongitude(double longtitude) {
            this.longitude = longtitude;
        }
        public double getLatitude() {
            return latitude;
        }
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
        public SkyStatus getSkyStatus() {
            return skyStatus;
        }
        public void setSkyStatus(SkyStatus skyStatus) {
            this.skyStatus = skyStatus;
        }
        public WeatherAmount getRainAmount() {
            return rainAmount;
        }
        public void setRainAmount(WeatherAmount rainAmount) {
            this.rainAmount = rainAmount;
        }
        public WeatherAmount getSnowAmount() {
            return snowAmount;
        }
        public void setSnowAmount(WeatherAmount snowAmount) {
            this.snowAmount = snowAmount;
        }
        public WeatherAmount getFogAmount() {
            return fogAmount;
        }
        public void setFogAmount(WeatherAmount fogAmount) {
            this.fogAmount = fogAmount;
        }
        public WeatherAmount getCloudAmount() {
            return cloudAmount;
        }
        public void setCloudAmount(WeatherAmount cloudAmount) {
            this.cloudAmount = cloudAmount;
        }
        public WindDirection getWindDirection() {
            return windDirection;
        }
        public void setWindDirection(WindDirection windDirection) {
            this.windDirection = windDirection;
        }
        public double getWindSpeed() {
            return windSpeed;
        }
        public void setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
        }


}
