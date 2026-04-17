package com.cms.model.car;

import com.cms.model.customer.Customer;

public class Sedan extends Car {
    public Sedan(String carId, String brand, String model, int year, double basePrice, Customer owner) {
        super(carId, brand, model, year, basePrice, owner);
    }

    @Override
    public String getCarCategory() {
        return "Sedan";
    }

    @Override
    public String getDrivingProfile() {
        return "Balanced daily driving with comfort-focused tuning.";
    }

    @Override
    public double getLaborRateMultiplier() {
        return 1.0;
    }

    @Override
    public double getRecommendedUpgradeBudget() {
        return getBasePrice() * 0.25;
    }
}
