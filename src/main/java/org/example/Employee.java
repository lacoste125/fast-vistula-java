package org.example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.example.Main.DATABASE_NAME;

@Data
//zadanie 1
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    String name;
    String surname;
    long salary;
    Gender gender;
    int section;

    public static int readFromFile(String fileName, Employee[] table) throws FileNotFoundException {
        int count = (int) Arrays.stream(
                        readData(fileName, table))
                .filter(Objects::nonNull)
                .count();
        System.out.println("Liczba odczytanych danych z pliku: " + count);

        return count;
    }

    public static Employee[] readData(String fileName) throws FileNotFoundException {
        return readData(fileName, new Employee[100]);
    }

    public static Employee[] readData(String fileName, Employee[] table) throws FileNotFoundException {
        if (table.length > 100) {
            throw new RuntimeException("Wielkość tablicy jest zbyt duża");
        }

        File file = new File(System.getProperty("user.dir") + File.separator + fileName);

        Scanner scanner;
        try {
            scanner = new Scanner(file).useDelimiter(" ");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Plik " + fileName + " nie został znaleziony. " +
                    "Popraw nazwę pliku i uruchom program ponownie.");
        }

        int i = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] row = line.split(" ");
            try {
                table[i] = new Employee(
                        row[0],
                        row[1],
                        Long.parseLong(row[2]),
                        Gender.valueOf(row[3]),
                        Integer.parseInt(row[4])
                );
            } catch (Exception e) {
                System.out.println("Niepoprawny format danych w lini o indeksie " + i + ". Nieprawidłowe dane: " + line);
            }
            i++;
        }

        return table;
    }

    public static double averageSalary(Employee[] table, Gender gender, int section) throws FileNotFoundException {
        Employee[] employees = readData(DATABASE_NAME, table);
        double average;
        try {
            average = Arrays.stream(employees).filter(Objects::nonNull)
                    .filter(s -> s.getGender() == gender)
                    .filter(s -> s.getSection() == section)
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .getAsDouble();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Nie ma udało się znaleźć danych z podanymi kryteriami. " +
                    "Obliczenia nie zostały wykonane.");
        }
        System.out.println("\nAvarage salary for gender " + gender + " section " + section + " is " + average + " PLN.");

        return average;
    }

    public static void zapiszDoPliku(String fileName, Employee[] employees) throws FileNotFoundException {
        File file = new File(fileName);
        PrintWriter writer = new PrintWriter(file);

        try (writer) {
            try {
                file.createNewFile();
                writer.print("");
            } catch (IOException e) {
                //ignore
            }

            for (Employee employee : employees) {
                writer.println(employee.getName() + " " +
                        employee.getSurname() + " " +
                        employee.getSalary() + " " +
                        employee.getGender().name() + " " +
                        employee.getSection()
                );
            }
        } catch (NullPointerException e) {
            //ignore
        }

        System.out.println("Dane zapisane do pliku " + fileName);
    }

    public static void printData(Employee[] employees) {
        Arrays.stream(employees)
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }
}