package com.cms.model.car;

import com.cms.model.customer.Customer;

public class SportsCar extends Car {
    public SportsCar(String carId, String brand, String model, int year, double basePrice, Customer owner) {
        super(carId, brand, model, year, basePrice, owner);
    }

    @Override
    public String getCarCategory() {
        return "Sports Car";
    }

    @Override
    public String getDrivingProfile() {
        return "Performance-first setup designed for speed, response, and premium handling.";
    }

    @Override
    public double getLaborRateMultiplier() {
        return 1.5;
    }

    @Override
    public double getRecommendedUpgradeBudget() {
        return getBasePrice() * 0.40;
    }
}
