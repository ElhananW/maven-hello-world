package com.myapp;

/**
 * Hello world program that greets the user.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Default name 
        String name = "Elhanan";
        
        // Update the name if an argument was provided
        if (args.length > 0) {
            name = args[0];
        }

        System.out.println("Hello World, " + name + "!");
    }
}
