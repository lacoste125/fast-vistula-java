package org.example;

import java.io.FileNotFoundException;
public class Main {
    public static final String DATABASE_NAME = "database.txt";

    public static void main(String[] args) throws FileNotFoundException {

        //zadanie 2
        Employee[] employees = new Employee[100];
        Employee.readFromFile(DATABASE_NAME, employees);
        System.out.println("\nDane odczytane z pliku: ");
        Employee.printData(employees);

        //zadanie 3
        Employee.averageSalary(employees, Gender.K, 3);

        //zadanie 4
        String nazwaPlikuDoZapisu = "kopyto.txt";
        Employee[] toWrite = new Employee[2];
        toWrite[0] = new Employee("Krysia", "Czubówna", 2566, Gender.K, 88);
        toWrite[1] = new Employee("Halina", "Pozdro600", 3333, Gender.M, 3);
        Employee.zapiszDoPliku(nazwaPlikuDoZapisu, toWrite);

        //zadanie 5
        Employee[] odczytane = Employee.readData(nazwaPlikuDoZapisu);
        System.out.println("\nDane odczytane z pliku: " + nazwaPlikuDoZapisu);
        Employee.printData(odczytane);
    }
}