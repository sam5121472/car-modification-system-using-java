package com.cms.service;

import com.cms.model.car.Car;
import com.cms.model.modification.Modification;

import java.util.Locale;

public class InvoiceService {
    private static final double SERVICE_TAX_RATE = 0.08;

    public String generateInvoice(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null.");
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Car Modification Invoice").append(System.lineSeparator());
        builder.append("--------------------------------------------------").append(System.lineSeparator());
        builder.append("Car ID: ").append(car.getCarId()).append(System.lineSeparator());
        builder.append("Vehicle: ").append(car.getDisplayName()).append(System.lineSeparator());
        builder.append("Owner: ").append(car.getOwner().getName()).append(System.lineSeparator());
        builder.append("Owner Budget Remaining: ").append(formatCurrency(car.getOwner().getAvailableBudget())).append(System.lineSeparator());
        builder.append(System.lineSeparator());
        builder.append("Applied Modifications").append(System.lineSeparator());

        if (car.getModifications().isEmpty()) {
            builder.append("No modifications applied yet.").append(System.lineSeparator());
        } else {
            int serial = 1;
            for (Modification modification : car.getModifications()) {
                double laborCost = modification.calculateLaborCost(car);
                double totalCost = modification.calculateTotalCost(car);

                builder.append(serial++)
                    .append(". ")
                    .append(modification.getName())
                    .append(" [")
                    .append(modification.getCategory())
                    .append("]")
                    .append(System.lineSeparator());
                builder.append("   Description: ").append(modification.getDescription()).append(System.lineSeparator());
                builder.append("   Specs: ").append(modification.getFeatureSummary()).append(System.lineSeparator());
                builder.append("   Parts Cost: ").append(formatCurrency(modification.getBaseCost())).append(System.lineSeparator());
                builder.append("   Labor Cost: ").append(formatCurrency(laborCost)).append(System.lineSeparator());
                builder.append("   Total Cost: ").append(formatCurrency(totalCost)).append(System.lineSeparator());
            }
        }

        double modificationSubtotal = car.getTotalModificationCost();
        double serviceTax = modificationSubtotal * SERVICE_TAX_RATE;
        double finalCustomizedValue = car.getBasePrice() + modificationSubtotal + serviceTax;

        builder.append("--------------------------------------------------").append(System.lineSeparator());
        builder.append("Base Car Value: ").append(formatCurrency(car.getBasePrice())).append(System.lineSeparator());
        builder.append("Modification Subtotal: ").append(formatCurrency(modificationSubtotal)).append(System.lineSeparator());
        builder.append("Service Tax (8%): ").append(formatCurrency(serviceTax)).append(System.lineSeparator());
        builder.append("Final Customized Value: ").append(formatCurrency(finalCustomizedValue)).append(System.lineSeparator());

        return builder.toString();
    }

    private String formatCurrency(double amount) {
        return String.format(Locale.US, "PKR %,.2f", amount);
    }
}
