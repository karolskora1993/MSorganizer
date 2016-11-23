package com.karolskora.msorgranizer.activities;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.karolskora.msorgranizer.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FirstRunTest {
    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class);
    @Before
    public void clearAppInfo() {
        Activity mActivity = mActivityTestRule.getActivity();
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(mActivity);
        prefs.edit().clear().commit();
        mActivity.deleteDatabase("ms_organizer.db");
    }
    @Test
    public void firstRunTest() {
        SystemClock.sleep(6000); //wait for splashscreen finish
        onView(withId(R.id.image_holo_light)).perform(click());
        onView(allOf(withId(R.id.initialSettingsButtonNext))).perform(click());
        onView(allOf(withId(R.id.nameTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed())).perform(replaceText("testName"), closeSoftKeyboard());
        onView(allOf(withId(R.id.doctorNameTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed())).perform(replaceText("doctortest"), closeSoftKeyboard());
        onView(allOf(withId(R.id.nurseNameTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed())).perform(replaceText("nurseTest"), closeSoftKeyboard());
        onView(allOf(withId(R.id.emailTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed())).perform(replaceText("email@test.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.initialSettingsButtonNext))).perform(click());
        onView(allOf(withId(R.id.firstInjectionButtonNext))).perform(scrollTo(), click());
        onView(allOf(withId(android.R.id.button1))).perform(click());
        onView(allOf(withId(R.id.dosesEditText),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed())).perform(replaceText("20"), closeSoftKeyboard());
        onView(allOf(withId(R.id.notificationDosesEditText),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed())).perform(replaceText("5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.button2),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed())).perform(click());
        onView(allOf(withId(R.id.initialSettingsButtonNext))).perform(scrollTo(), click());
    }
}

