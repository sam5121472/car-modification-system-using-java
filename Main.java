package com.cms.app;

import com.cms.exception.BudgetExceededException;
import com.cms.exception.ModificationNotAllowedException;
import com.cms.model.car.Car;
import com.cms.model.modification.Modification;
import com.cms.service.InvoiceService;
import com.cms.service.ModificationGarage;
import com.cms.util.SampleData;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private final ModificationGarage garage;
    private final List<Modification> catalog;
    private final InvoiceService invoiceService;
    private final Scanner scanner;

    public Main() {
        this.garage = SampleData.createGarage();
        this.catalog = SampleData.createCatalog();
        this.invoiceService = new InvoiceService();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main application = new Main();

        if (args.length > 0 && "demo".equalsIgnoreCase(args[0])) {
            application.runGuidedDemo();
            return;
        }

        application.startInteractiveMenu();
    }

    private void startInteractiveMenu() {
        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showRegisteredCars();
                    break;
                case "2":
                    showCarDetails();
                    break;
                case "3":
                    showModificationCatalog();
                    break;
                case "4":
                    applyModificationFlow();
                    break;
                case "5":
                    generateInvoiceFlow();
                    break;
                case "6":
                    runGuidedDemo();
                    break;
                case "0":
                    running = false;
                    System.out.println("Exiting Car Modification System. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid menu number.");
            }
        }

        scanner.close();
    }

    private void printWelcomeBanner() {
        System.out.println("==================================================");
        System.out.println("         CAR MODIFICATION SYSTEM (JAVA)           ");
        System.out.println("==================================================");
        System.out.println("Concepts covered: Abstraction, Encapsulation, Inheritance");
        System.out.println();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. View registered cars");
        System.out.println("2. View detailed car profile");
        System.out.println("3. View modification catalog");
        System.out.println("4. Apply modification to a car");
        System.out.println("5. Generate invoice");
        System.out.println("6. Run guided demo");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private void showRegisteredCars() {
        System.out.println();
        System.out.println(garage.getGarageOverview());
    }

    private void showCarDetails() {
        System.out.print("Enter car ID: ");
        String carId = scanner.nextLine();

        try {
            Car car = garage.getCarById(carId);
            System.out.println();
            System.out.println(car.getBuildSheet());
        } catch (NoSuchElementException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void showModificationCatalog() {
        System.out.println();
        System.out.println("Available Modifications");
        System.out.println("--------------------------------------------------");

        for (int index = 0; index < catalog.size(); index++) {
            Modification modification = catalog.get(index);
            System.out.println((index + 1) + ". " + modification.getName() + " [" + modification.getCategory() + "]");
            System.out.println("   Description: " + modification.getDescription());
            System.out.println("   Highlights: " + modification.getFeatureSummary());
            System.out.println("   Compatibility: " + modification.getCompatibilityNote());
            System.out.println(
                String.format(Locale.US, "   Base Cost: PKR %,.2f", modification.getBaseCost())
            );
        }
    }

    private void applyModificationFlow() {
        try {
            System.out.print("Enter car ID: ");
            String carId = scanner.nextLine();
            Car selectedCar = garage.getCarById(carId);

            System.out.println("Selected Car: " + selectedCar.getDisplayName());
            showModificationCatalog();

            System.out.print("Choose modification number: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());
            Modification selectedModification = getModificationByChoice(choice);

            String result = garage.applyModification(selectedCar.getCarId(), selectedModification);
            System.out.println(result);
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a valid numeric option.");
        } catch (NoSuchElementException | IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        } catch (BudgetExceededException | ModificationNotAllowedException exception) {
            System.out.println("Operation failed: " + exception.getMessage());
        }
    }

    private void generateInvoiceFlow() {
        System.out.print("Enter car ID: ");
        String carId = scanner.nextLine();

        try {
            Car car = garage.getCarById(carId);
            System.out.println();
            System.out.println(invoiceService.generateInvoice(car));
        } catch (NoSuchElementException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Modification getModificationByChoice(int choice) {
        if (choice < 1 || choice > catalog.size()) {
            throw new IllegalArgumentException("Selected modification number is out of range.");
        }
        return catalog.get(choice - 1);
    }

    private void runGuidedDemo() {
        System.out.println();
        System.out.println("Guided Demo");
        System.out.println("--------------------------------------------------");
        System.out.println("This demo applies valid and invalid modifications to show business rules.");
        System.out.println();
        System.out.println(garage.getGarageOverview());

        attemptDemoModification("CAR-101", 3);
        attemptDemoModification("CAR-202", 4);
        attemptDemoModification("CAR-303", 5);
        attemptDemoModification("CAR-101", 4);

        System.out.println();
        System.out.println("Invoice Snapshot: CAR-101");
        System.out.println(invoiceService.generateInvoice(garage.getCarById("CAR-101")));
    }

    private void attemptDemoModification(String carId, int catalogNumber) {
        try {
            Modification modification = getModificationByChoice(catalogNumber);
            String result = garage.applyModification(carId, modification);
            System.out.println(result);
        } catch (BudgetExceededException | ModificationNotAllowedException | IllegalArgumentException exception) {
            System.out.println("Demo step failed: " + exception.getMessage());
        }
    }
}
