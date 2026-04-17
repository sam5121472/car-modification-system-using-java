package com.cms.service;

import com.cms.exception.BudgetExceededException;
import com.cms.exception.ModificationNotAllowedException;
import com.cms.model.car.Car;
import com.cms.model.modification.Modification;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

public class ModificationGarage {
    private final Map<String, Car> carRegistry;

    public ModificationGarage() {
        this.carRegistry = new LinkedHashMap<>();
    }

    public void registerCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null.");
        }
        if (carRegistry.containsKey(car.getCarId())) {
            throw new IllegalArgumentException("Car with ID " + car.getCarId() + " is already registered.");
        }
        carRegistry.put(car.getCarId(), car);
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(carRegistry.values());
    }

    public Car getCarById(String carId) {
        String normalizedId = normalizeId(carId);
        for (Map.Entry<String, Car> entry : carRegistry.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(normalizedId)) {
                return entry.getValue();
            }
        }
        throw new NoSuchElementException("No car found with ID " + normalizedId + ".");
    }

    public String applyModification(String carId, Modification modification)
        throws BudgetExceededException, ModificationNotAllowedException {
        Car car = getCarById(carId);

        if (modification == null) {
            throw new IllegalArgumentException("Modification cannot be null.");
        }

        double modificationCost = modification.calculateTotalCost(car);
        double projectedUpgradeTotal = car.getTotalModificationCost() + modificationCost;

        if (projectedUpgradeTotal > car.getRecommendedUpgradeBudget()) {
            throw new BudgetExceededException(
                "Applying " + modification.getName() + " would exceed the recommended upgrade budget for " + car.getDisplayName() + "."
            );
        }

        if (modificationCost > car.getOwner().getAvailableBudget()) {
            throw new BudgetExceededException(
                car.getOwner().getName() + " does not have enough remaining budget for " + modification.getName() + "."
            );
        }

        car.addModification(modification);
        car.getOwner().deductBudget(modificationCost);

        return String.format(
            Locale.US,
            "%s applied to %s for PKR %,.2f",
            modification.getName(),
            car.getDisplayName(),
            modificationCost
        );
    }

    public String getGarageOverview() {
        StringBuilder builder = new StringBuilder();
        builder.append("Registered Cars").append(System.lineSeparator());
        builder.append("----------------------------------------").append(System.lineSeparator());

        if (carRegistry.isEmpty()) {
            builder.append("No cars have been registered yet.");
            return builder.toString();
        }

        for (Car car : carRegistry.values()) {
            builder.append(car.getCarId())
                .append(" | ")
                .append(car.getDisplayName())
                .append(" | ")
                .append(car.getCarCategory())
                .append(" | Owner: ")
                .append(car.getOwner().getName())
                .append(" | Mods: ")
                .append(car.getModifications().size())
                .append(System.lineSeparator());
        }

        return builder.toString();
    }

    private String normalizeId(String carId) {
        if (carId == null || carId.trim().isEmpty()) {
            throw new IllegalArgumentException("Car ID cannot be empty.");
        }
        return carId.trim();
    }
}
