package com.zup.suite;

import com.zup.test.AmericanasTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Americanas tests.")
@SelectClasses({AmericanasTest.class})
public class AmericanasSuite {
    private AmericanasSuite() {

    }
}
