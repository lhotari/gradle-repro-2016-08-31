package org.gradle.test.performance1_1;

import static org.junit.Assert.*;

public class Test1_4 {
    private final Production1_4 production = new Production1_4("value");

    @org.junit.Test
    public void test() {
        assertEquals(production.getProperty(), "value");
    }
}