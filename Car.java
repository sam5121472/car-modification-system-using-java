package com.cms.model.car;

import com.cms.exception.ModificationNotAllowedException;
import com.cms.model.customer.Customer;
import com.cms.model.modification.Modification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public abstract class Car {
    private final String carId;
    private String brand;
    private String model;
    private int year;
    private double basePrice;
    private final Customer owner;
    private final List<Modification> modifications;

    public Car(String carId, String brand, String model, int year, double basePrice, Customer owner) {
        this.carId = requireText(carId, "Car ID");
        this.brand = requireText(brand, "Brand");
        this.model = requireText(model, "Model");
        this.year = requireYear(year);
        this.basePrice = requirePositive(basePrice, "Base price");
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }
        this.owner = owner;
        this.modifications = new ArrayList<>();
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = requireText(brand, "Brand");
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = requireText(model, "Model");
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = requireYear(year);
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = requirePositive(basePrice, "Base price");
    }

    public Customer getOwner() {
        return owner;
    }

    public List<Modification> getModifications() {
        return Collections.unmodifiableList(modifications);
    }

    public String getDisplayName() {
        return year + " " + brand + " " + model;
    }

    public void addModification(Modification modification) throws ModificationNotAllowedException {
        if (modification == null) {
            throw new IllegalArgumentException("Modification cannot be null.");
        }

        if (!modification.isCompatibleWith(this)) {
            throw new ModificationNotAllowedException(
                modification.getName() + " is not compatible with " + getDisplayName() + "."
            );
        }

        if (hasModification(modification.getModificationId())) {
            throw new ModificationNotAllowedException(
                modification.getName() + " has already been applied to " + getCarId() + "."
            );
        }

        modifications.add(modification);
    }

    public boolean hasModification(String modificationId) {
        for (Modification modification : modifications) {
            if (modification.getModificationId().equalsIgnoreCase(modificationId)) {
                return true;
            }
        }
        return false;
    }

    public double getTotalModificationCost() {
        double total = 0;
        for (Modification modification : modifications) {
            total += modification.calculateTotalCost(this);
        }
        return total;
    }

    public double getEstimatedFinalValue() {
        return basePrice + getTotalModificationCost();
    }

    public String getBuildSheet() {
        StringBuilder builder = new StringBuilder();
        builder.append("Car ID: ").append(carId).append(System.lineSeparator());
        builder.append("Category: ").append(getCarCategory()).append(System.lineSeparator());
        builder.append("Vehicle: ").append(getDisplayName()).append(System.lineSeparator());
        builder.append("Owner: ").append(owner.getName()).append(System.lineSeparator());
        builder.append("Driving Profile: ").append(getDrivingProfile()).append(System.lineSeparator());
        builder.append(
            String.format(Locale.US, "Base Price: PKR %,.2f%n", basePrice)
        );
        builder.append(
            String.format(Locale.US, "Recommended Upgrade Budget: PKR %,.2f%n", getRecommendedUpgradeBudget())
        );
        builder.append(
            String.format(Locale.US, "Current Upgrade Cost: PKR %,.2f%n", getTotalModificationCost())
        );
        builder.append("Applied Modifications: ").append(modifications.size()).append(System.lineSeparator());

        if (modifications.isEmpty()) {
            builder.append("No modifications have been applied yet.");
        } else {
            for (int index = 0; index < modifications.size(); index++) {
                Modification modification = modifications.get(index);
                builder.append(index + 1)
                    .append(". ")
                    .append(modification.getName())
                    .append(" - ")
                    .append(modification.getFeatureSummary())
                    .append(System.lineSeparator());
            }
        }

        return builder.toString();
    }

    public abstract String getCarCategory();

    public abstract String getDrivingProfile();

    public abstract double getLaborRateMultiplier();

    public abstract double getRecommendedUpgradeBudget();

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return value.trim();
    }

    private static int requireYear(int year) {
        if (year < 1990 || year > 2035) {
            throw new IllegalArgumentException("Year must be between 1990 and 2035.");
        }
        return year;
    }

    private static double requirePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than zero.");
        }
        return value;
    }
}
