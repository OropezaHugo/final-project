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
        // Crear un objeto ComparisonData para la prueba
        ComparisonData comparisonData = new ComparisonData(30, "Single", 0);

        // Llamar al método findSimilarUsers
        List<User> similarUsers = userMatcher.findSimilarUsers(comparisonData);

        // Verificar que la lista no sea nula
        Assert.assertNotNull(similarUsers);

        // Verificar que la lista tenga un máximo de 5 usuarios
        Assert.assertTrue(similarUsers.size() <= 5);

        // Verificar que los usuarios tengan el mismo estado civil que el ComparisonData utilizado en la prueba
        for (User user : similarUsers) {
            Assert.assertEquals("Single", user.getCivilStatus());
        }
    }

    @Test
    public void testFindSimilarUsers_EmptyUsersList() {
        // Crear un objeto ComparisonData para la prueba
        ComparisonData comparisonData = new ComparisonData(25, "Married", 1);

        // Modificar la lista de usuarios para que esté vacía
        userMatcher.setUsers(Arrays.asList());

        // Llamar al método findSimilarUsers
        List<User> similarUsers = userMatcher.findSimilarUsers(comparisonData);

        // Verificar que la lista esté vacía
        Assert.assertTrue(similarUsers.isEmpty());
    }

    // Otros métodos de prueba...

}
