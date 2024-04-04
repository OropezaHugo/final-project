package com.intuit.fuzzymatcher.function;

import com.intuit.fuzzymatcher.domain.Element;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;



public class RecommendationGeneratorTest {

    RecommendationGenerator recommendationGenerator = new RecommendationGenerator();

    @Test
    public void testGenerateRecommendation_SimpleCase() {
        String userName = "John";
        List<String> weaponNames = Arrays.asList("Sword", "Axe", "Johalin", "Staff", "Dagger");

        String recommendation = recommendationGenerator.generateRecommendation(userName, weaponNames);

        Assert.assertEquals("Johalin", recommendation);
    }

    @Test
    public void testGenerateRecommendation_EmptyWeaponList() {
        String userName = "Alice";
        List<String> weaponNames = Arrays.asList();

        String recommendation = recommendationGenerator.generateRecommendation(userName, weaponNames);

        Assert.assertEquals(null, recommendation);
    }

    @Test
    public void testGenerateRecommendation_UserNameIsSubstringOfWeaponNames() {
        String userName = "Alex";
        List<String> weaponNames = Arrays.asList("Alexis", "Gato", "Casa");

        String recommendation = recommendationGenerator.generateRecommendation(userName, weaponNames);

        Assert.assertEquals("Alexis", recommendation);
    }

    @Test
    public void testGenerateRecommendation_LongUserName() {
        String userName = "Swonder";
        String weaponCsvFile = "src/test/resources/items.csv";

        List<String> weaponNames = recommendationGenerator.loadWeaponNamesFromCSV(weaponCsvFile);

        String recommendation = recommendationGenerator.generateRecommendation(userName, weaponNames);

        Assert.assertEquals("Sword", recommendation);
    }

    @Test
    public void testGenerateRecommendation_LongestCommonSubstring() {
        String userName = "David";
        String weaponCsvFile = "src/test/resources/items.csv";

        List<String> weaponNames = recommendationGenerator.loadWeaponNamesFromCSV(weaponCsvFile);

        String recommendation = recommendationGenerator.generateRecommendation(userName, weaponNames);



        Assert.assertEquals(null, recommendation);
    }


    public static void main(String[] args) {

        RecommendationGenerator recommendationGenerator = new RecommendationGenerator();
        String userCsvFile = "src/test/resources/users.csv";
        String weaponCsvFile = "src/test/resources/items.csv";

        List<String> weaponNames = recommendationGenerator.loadWeaponNamesFromCSV(weaponCsvFile);
        List<String> userNames = recommendationGenerator.loadUserNamesFromCSV(userCsvFile);

        for (String userName : userNames) {
            String recommendation = recommendationGenerator.generateRecommendation(userName, weaponNames);
            System.out.println("Recommended Weapon for " + userName + ": " + recommendation);
        }
    }



}
