package com.intuit.fuzzymatcher.Recommendation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserMatcher {
    private List<User> users;

    // Constructor que carga los usuarios desde un archivo CSV
    public UserMatcher(String csvFilePath) {
        this.users = loadUsersFromCSV(csvFilePath);
    }

    // Método privado para cargar usuarios desde un archivo CSV
    private List<User> loadUsersFromCSV(String csvFile) {
        List<User> loadedUsers = new ArrayList<>();
        String line;
        String delimiter = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Leer la primera línea (encabezado) y descartarla
            br.readLine();
            // Leer las líneas restantes que contienen datos de usuario
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                if (parts.length >= 6) { // Verificar que la línea tenga al menos 6 partes (campos)
                    String name = parts[0].trim();
                    int ic = Integer.parseInt(parts[1].trim());
                    int age = Integer.parseInt(parts[2].trim());
                    double height = Double.parseDouble(parts[3].trim());
                    String civilStatus = parts[4].trim();
                    int numberOfCouples = Integer.parseInt(parts[5].trim());
                    // Crear un nuevo usuario y agregarlo a la lista
                    User user = new User(name, ic, age, height, civilStatus, numberOfCouples);
                    loadedUsers.add(user);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return loadedUsers;
    }

    // Método para encontrar usuarios similares basados en datos de comparación
    public List<User> findSimilarUsers(ComparisonData userData) {
        // Filtrar usuarios con el mismo estado civil
        List<User> similarUsers = users.stream()
                .filter(u -> u.getCivilStatus().equals(userData.getCivilStatus()))
                .collect(Collectors.toList());

        // Ordenar por diferencia de edad
        similarUsers.sort(Comparator.comparingInt(u -> Math.abs(u.getAge() - userData.getAge())));

        // Ordenar por diferencia en el número de parejas
        similarUsers.sort(Comparator.comparingInt(u -> Math.abs(u.getNumberOfCouples() - userData.getNumberOfCouples())));

        // Limitar la cantidad de usuarios similares devueltos
        return similarUsers.subList(0, Math.min(similarUsers.size(), 5));
    }

    // Método para generar los nombres de los usuarios más similares
    public List<String> generateTop5UserNames(ComparisonData userData) {
        List<User> similarUsers = findSimilarUsers(userData);
        List<String> top5UserNames = new ArrayList<>();
        for (User user : similarUsers) {
            top5UserNames.add(user.getName());
        }
        return top5UserNames;
    }

    public void setUsers(List<Object> asList) {
    }

    // Otros métodos de la clase...
}
