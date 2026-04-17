# Car Modification System

An intermediate-level Java console project that demonstrates abstraction, encapsulation, and inheritance through a practical car customization workflow.

## What This Project Covers

- Registering different types of cars
- Managing customers and upgrade budgets
- Applying compatible modifications
- Preventing invalid or over-budget upgrades
- Generating invoices for customized cars
- Running the project in both interactive mode and guided demo mode

## OOP Concepts Used

- Abstraction:
  `Car` is an abstract base class and `Modification` is an abstract base class. Both define common behavior while leaving type-specific logic to subclasses.
- Encapsulation:
  Fields are private and controlled through constructors and methods with validation in classes like `Customer`, `Car`, and `Modification`.
- Inheritance:
  `Sedan`, `SUV`, and `SportsCar` inherit from `Car`. Modification types such as `PaintJob`, `PerformanceTune`, and `OffRoadPackage` inherit from `Modification`.

## Project Structure

```text
CarModificationSystem/
|-- README.md
`-- src/
    `-- com/cms/
        |-- app/
        |-- exception/
        |-- model/
        |   |-- car/
        |   |-- customer/
        |   `-- modification/
        |-- service/
        `-- util/
```

## Compile

```powershell
powershell -ExecutionPolicy Bypass -File .\build.ps1
```

## Run Interactive Menu

```powershell
powershell -ExecutionPolicy Bypass -File .\run-app.ps1
```

## Run Guided Demo

```powershell
powershell -ExecutionPolicy Bypass -File .\run-demo.ps1
```

These scripts automatically look for a local JDK in common Windows locations such as `JAVA_HOME`, `C:\Program Files\Java\latest`, and `C:\Program Files\Java\jdk-26`.

## Suggested Study Flow

1. Start with `Car` and `Modification` to understand the abstraction.
2. Move to the subclasses like `Sedan`, `SUV`, `SportsCar`, `PaintJob`, and `AeroKit`.
3. Read `ModificationGarage` and `InvoiceService` to see how the business logic is organized.
4. Run `Main` in demo mode to observe the end-to-end workflow.
