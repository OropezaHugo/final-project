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

    public UserMatcher(String csvFilePath) {
        this.users = loadUsersFromCSV(csvFilePath);
    }

    private List<User> loadUsersFromCSV(String csvFile) {
        List<User> loadedUsers = new ArrayList<>();
        String line;
        String delimiter = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                if (parts.length >= 6) {
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

    public List<User> findSimilarUsers(ComparisonData userData) {
        // Filtrar usuarios con el mismo estado civil
        List<User> similarUsers = users.stream()
                .filter(u -> u.getCivilStatus().equals(userData.getCivilStatus()))
                .collect(Collectors.toList());

        similarUsers.sort(Comparator.comparingInt(u -> Math.abs(u.getAge() - userData.getAge())));

        similarUsers.sort(Comparator.comparingInt(u -> Math.abs(u.getNumberOfCouples() - userData.getNumberOfCouples())));

        return similarUsers.subList(0, Math.min(similarUsers.size(), 5));
    }

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
}
