package com.intuit.fuzzymatcher.function;

import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ElementType;
import com.intuit.fuzzymatcher.domain.Token;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationGenerator {


    public String generateRecommendation(String userName, List<String> weaponNames) {

        String processedUserName = PreProcessFunction.toLowerCase().andThen(PreProcessFunction.removeSpecialChars()).apply(userName);


        List<Token<String>> userTokens = (List<Token<String>>) TokenizerFunction.wordTokenizer().apply(new Element.Builder<String>().setType(ElementType.NAME).setValue(processedUserName).createElement())
                .collect(Collectors.toList());


        double maxSimilarity = 0;
        String recommendedWeapon = null;


        for (String weaponName : weaponNames) {

            String processedWeaponName = PreProcessFunction.toLowerCase().andThen(PreProcessFunction.removeSpecialChars()).apply(weaponName);


            List<Token<String>> weaponTokens = (List<Token<String>>) TokenizerFunction.wordTokenizer().apply(new Element.Builder<String>().setType(ElementType.NAME).setValue(processedWeaponName).createElement())
                    .collect(Collectors.toList());


            double similarity = calculateSimilarity(userTokens, weaponTokens);


            if (similarity >= 0.2 && similarity > maxSimilarity) {
                maxSimilarity = similarity;
                recommendedWeapon = weaponName;
            }
        }

        return recommendedWeapon;
    }


    private double calculateSimilarity(List<Token<String>> userTokens, List<Token<String>> weaponTokens) {

        double totalSimilarity = 0;
        for (Token<String> userToken : userTokens) {
            for (Token<String> weaponToken : weaponTokens) {
                double tokenSimilarity = calculateTokenSimilarity(userToken, weaponToken);
                totalSimilarity += tokenSimilarity;
            }
        }
        return totalSimilarity / (userTokens.size() * weaponTokens.size());
    }


    private double calculateTokenSimilarity(Token<String> token1, Token<String> token2) {

        String value1 = token1.getValue();
        String value2 = token2.getValue();
        int commonLength = longestCommonSubstring(value1, value2).length();
        return (double) commonLength / (value1.length() + value2.length());
    }


    private String longestCommonSubstring(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        int maxLength = 0;
        int endIndex = 0;
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i - 1;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return str1.substring(endIndex - maxLength + 1, endIndex + 1);
    }

    public List<String> loadWeaponNamesFromCSV(String csvFile) {
        List<String> weaponNames = new ArrayList<>();
        String line;
        String delimiter = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                if (parts.length > 0) {
                    weaponNames.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weaponNames;
    }
    public List<String> loadUserNamesFromCSV(String csvFile) {
        List<String> userNames = new ArrayList<>();
        String line;
        String delimiter = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(delimiter);
                if (parts.length > 0) {
                    String userName = parts[0].trim();
                    String[] nameParts = userName.split("\\s+");
                    userNames.add(nameParts[0]); // Tomar solo el primer nombre
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userNames;
    }


}
