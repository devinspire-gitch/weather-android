package com.canada.weather.Utils;

public class Constants {
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final String UNITS_C = "metric";
    public static final String UNITS_F = "imperial";
    public static final String[] DAYS_OF_WEEK = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
    };
    public static final String[] MONTH_NAME = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };


    public static final String[] WEATHER_STATUS = {
            "Thunderstorm",
            "Drizzle",
            "Rain",
            "Snow",
            "Atmosphere",
            "Clear",
            "Few Clouds",
            "Broken Clouds",
            "Cloud"
    };

    public static final String CITY_INFO = "city-info";

    public static final long TIME_TO_PASS = 6 * 600000;

    public static final String LAST_STORED_CURRENT = "last-stored-current";
    public static final String LAST_STORED_MULTIPLE_DAYS = "last-stored-multiple-days";

    public static final String API_KEY = "e3e24ce2c870b6260bdd8631bde5b391";
    public static final String FIVE_DAY_WEATHER_ITEM = "five-day-weather-item";
}

