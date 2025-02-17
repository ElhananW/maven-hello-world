package com.myapp;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out; // Save the original System.out

    /**
     * Setup method runs before each test case.
     */
    @Before
    public void setUp() {
        // Redirect System.out to our ByteArrayOutputStream
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Cleanup method runs after each test case.
     */
    @After
    public void restoreStream() {
        // Restore the original System.out after tests
        System.setOut(originalOut);
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue(true);
    }

    @Test
    public void testAppOutput() {
        // Default name check
        String[] args = {};
        App.main(args);  // Run the application with the default name
        assertTrue(outContent.toString().contains("Hello World, Elhanan!"));

        // Custom name check
        String[] argsWithName = {"John"};
        App.main(argsWithName);  // Run the application with a custom name
        assertTrue(outContent.toString().contains("Hello World, John!"));
    }
}
