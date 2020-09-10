package com.zup;

import com.zup.suite.AmericanasSuite;
import org.junit.runner.JUnitCore;

public class Main {
    public static void main(String[] args) {
        new JUnitCore().run(AmericanasSuite.class);
    }
}
