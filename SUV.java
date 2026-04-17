package com.cms.model.car;

import com.cms.model.customer.Customer;

public class SUV extends Car {
    public SUV(String carId, String brand, String model, int year, double basePrice, Customer owner) {
        super(carId, brand, model, year, basePrice, owner);
    }

    @Override
    public String getCarCategory() {
        return "SUV";
    }

    @Override
    public String getDrivingProfile() {
        return "Utility-focused build suited for family travel and rough terrain.";
    }

    @Override
    public double getLaborRateMultiplier() {
        return 1.2;
    }

    @Override
    public double getRecommendedUpgradeBudget() {
        return getBasePrice() * 0.30;
    }
}
