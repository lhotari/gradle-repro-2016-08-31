package org.gradle.test.performance2_1;

import static org.junit.Assert.*;

public class Test2_4 {
    private final Production2_4 production = new Production2_4("value");

    @org.junit.Test
    public void test() {
        assertEquals(production.getProperty(), "value");
    }
}