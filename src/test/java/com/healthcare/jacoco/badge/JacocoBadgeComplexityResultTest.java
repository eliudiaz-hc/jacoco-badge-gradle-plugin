package com.healthcare.jacoco.badge;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class JacocoBadgeComplexityResultTest {

    @Mock
    private JacocoBadgePercentageResult method;
    @Mock
    private JacocoBadgePercentageResult that;


    @BeforeMethod
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider
    private Object[][] test() {
        return new Object[][]{
                new Object[]{120, 0, 60, "COMPLEXITY:2.00",
                        "https://img.shields.io/badge/complexity-2.00-brightgreen.svg"},
                new Object[]{120, 0, 8, "COMPLEXITY:15.00",
                        "https://img.shields.io/badge/complexity-15.00-yellow.svg"},
                new Object[]{120, 0, 4, "COMPLEXITY:30.00",
                        "https://img.shields.io/badge/complexity-30.00-orange.svg"},
                new Object[]{120, 0, 2, "COMPLEXITY:60.00",
                        "https://img.shields.io/badge/complexity-60.00-red.svg"},
        };
    }

    @Test(dataProvider = "test")
    public void test(int thatCovered, int thatMissed, int methodCovered, String expectString,
                     String expectBadge) {
        when(that.type()).thenReturn(JacocoBadgePercentageResult.Type.COMPLEXITY);
        when(that.covered()).thenReturn(thatCovered);
        when(that.missed()).thenReturn(thatMissed);

        when(method.covered()).thenReturn(methodCovered);

        JacocoBadgeComplexityResult result = new JacocoBadgeComplexityResult(that, method);
        assertEquals(result.toString(), expectString);
        assertEquals(result.badgeUrl(), expectBadge);
    }
}
