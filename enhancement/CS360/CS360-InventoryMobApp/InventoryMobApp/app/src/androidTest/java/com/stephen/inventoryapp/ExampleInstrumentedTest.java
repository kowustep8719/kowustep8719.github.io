// Stephen Inventory Mobile App

package com.stephen.inventoryapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;

import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 * <p>
 * '@check' <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

// Use the AndroidJUnit4 test runner for this test class
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Retrieve the context of the app under test
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        // Assert that the package name matches the expected value
        assertEquals("com.zybooks.inventoryapp", appContext.getPackageName());
    }
}





