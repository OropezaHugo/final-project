package com.intuit.fuzzymatcher.Recommendations;

import com.intuit.fuzzymatcher.Recommendation.ComparisonData;
import com.intuit.fuzzymatcher.Recommendation.User;
import com.intuit.fuzzymatcher.Recommendation.UserMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

public class UserMatcherTest {

    private UserMatcher userMatcher;

    @Before
    public void setUp() {
        // Inicializar el UserMatcher con un archivo CSV de prueba
        String csvFilePath = "src/test/resources/users.csv";
        userMatcher = new UserMatcher(csvFilePath);
    }

    @Test
    public void testFindSimilarUsers_SimpleCase() {
        ComparisonData comparisonData = new ComparisonData(30, "Single", 0);
        List<User> similarUsers = userMatcher.findSimilarUsers(comparisonData);
        Assert.assertNotNull(similarUsers);
        Assert.assertTrue(similarUsers.size() <= 5);
        for (User user : similarUsers) {
            Assert.assertEquals("Single", user.getCivilStatus());
        }
    }

    @Test
    public void testFindSimilarUsers_EmptyUsersList() {
        ComparisonData comparisonData = new ComparisonData(25, "Married", 1);
        userMatcher.setUsers(Arrays.asList());
        List<User> similarUsers = userMatcher.findSimilarUsers(comparisonData);
        Assert.assertTrue(similarUsers.isEmpty());
    }

    @Test
    public void testGenerateTop5UserNames() {
        ComparisonData comparisonData = new ComparisonData(30, "Single", 0);
        List<String> top5UserNames = userMatcher.generateTop5UserNames(comparisonData);
        Assert.assertNotNull(top5UserNames);
        Assert.assertTrue(top5UserNames.size() <= 5);
        for (String userName : top5UserNames) {
            Assert.assertNotNull(userName);
            Assert.assertFalse(userName.isEmpty());
        }
    }


}
