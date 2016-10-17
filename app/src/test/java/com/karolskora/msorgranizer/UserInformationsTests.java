package com.karolskora.msorgranizer;

import android.test.AndroidTestCase;

import com.karolskora.msorgranizer.activities.UserInformationsActivity;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserInformationsTests extends AndroidTestCase{

    @Test
    public void areFieldsFieldMethod throws Exception {

        assertEquals(false,new UserInformationsForTest().areFieldsFilled());
}

private class UserInformationsForTest extends UserInformationsActivity {
    public boolean areFieldsFilled() {
        super.areFieldsFilled();
    }
}