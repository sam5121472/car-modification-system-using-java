package com.cms.util;

import com.cms.model.car.SUV;
import com.cms.model.car.Sedan;
import com.cms.model.car.SportsCar;
import com.cms.model.customer.Customer;
import com.cms.model.modification.AeroKit;
import com.cms.model.modification.InteriorUpgrade;
import com.cms.model.modification.Modification;
import com.cms.model.modification.OffRoadPackage;
import com.cms.model.modification.PaintJob;
import com.cms.model.modification.PerformanceTune;
import com.cms.service.ModificationGarage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SampleData {
    private SampleData() {
    }

    public static ModificationGarage createGarage() {
        ModificationGarage garage = new ModificationGarage();

        Customer customerOne = new Customer("CUST-101", "Ali Khan", "0300-1111111", 1000000);
        Customer customerTwo = new Customer("CUST-202", "Ayesha Malik", "0300-2222222", 1300000);
        Customer customerThree = new Customer("CUST-303", "Hamza Raza", "0300-3333333", 2000000);

        garage.registerCar(new Sedan("CAR-101", "Honda", "Civic RS", 2022, 7200000, customerOne));
        garage.registerCar(new SUV("CAR-202", "Toyota", "Fortuner", 2021, 12500000, customerTwo));
        garage.registerCar(new SportsCar("CAR-303", "Ford", "Mustang GT", 2023, 16500000, customerThree));

        return garage;
    }

    public static List<Modification> createCatalog() {
        List<Modification> catalog = new ArrayList<>();

        catalog.add(
            new PaintJob(
                "MOD-01",
                "Premium Paint Job",
                "A full-body repaint with protective finish.",
                120000,
                "Midnight Black",
                "Glossy"
            )
        );

        catalog.add(
            new InteriorUpgrade(
                "MOD-02",
                "Luxury Interior Package",
                "Upgrades seats, trims, and cabin mood lighting.",
                180000,
                "Nappa Leather",
                true
            )
        );

        catalog.add(
            new PerformanceTune(
                "MOD-03",
                "Stage 2 Performance Tune",
                "Engine optimization with intake and ECU remap.",
                250000,
                90,
                true
            )
        );

        catalog.add(
            new OffRoadPackage(
                "MOD-04",
                "Adventure Off-Road Kit",
                "Suspension lift, all-terrain setup, and underbody protection.",
                300000,
                2.5,
                true
            )
        );

        catalog.add(
            new AeroKit(
                "MOD-05",
                "Carbon Aero Kit",
                "Front splitter, side skirts, diffuser, and rear wing.",
                350000,
                "High",
                true
            )
        );

        return Collections.unmodifiableList(catalog);
    }
}
