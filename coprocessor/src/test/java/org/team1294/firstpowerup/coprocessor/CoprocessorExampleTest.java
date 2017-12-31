package org.team1294.firstpowerup.coprocessor;

import static org.junit.Assert.*;

import static org.team1294.firstpowerup.coprocessor.CoprocessorExample
        .exampleAdd;

/**
 * @author Austin Jenchi (timtim17)
 */
public class CoprocessorExampleTest {
    @org.junit.Test
    public void testExampleAdd() {
        assertEquals(0, exampleAdd(0, 0));
        assertEquals(-3, exampleAdd(-1, -2));
        assertEquals(2, exampleAdd(1, 1));
        assertEquals(1, exampleAdd(3, -2));
    }
}