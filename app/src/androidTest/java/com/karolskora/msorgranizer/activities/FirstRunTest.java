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
        ViewInteraction appCompatImageView = onView(
                withId(R.id.image_holo_light));
        appCompatImageView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.initialSettingsButtonNext)));
        appCompatButton.perform(click());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.nameTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed()));
        editText2.perform(replaceText("testName"), closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.doctorNameTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed()));
        editText3.perform(replaceText("doctortest"), closeSoftKeyboard());

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.nurseNameTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed()));
        editText4.perform(replaceText("nurseTest"), closeSoftKeyboard());

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.emailTextEdit),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed()));
        editText6.perform(replaceText("email@test.com"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(R.id.initialSettingsButtonNext)));
        button.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.firstInjectionButtonNext)));
        button2.perform(scrollTo(), click());

        ViewInteraction button3 = onView(
                allOf(withId(android.R.id.button1)));
        button3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.dosesEditText),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("20"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.notificationDosesEditText),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button2),
                        withParent(withId(R.id.aboutUserFragment)),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction button4 = onView(
                allOf(withId(R.id.initialSettingsButtonNext)));
        button4.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.fragmentToInjectionButton),
                        withParent(allOf(withId(R.id.fragment_to_injection),
                                withParent(withId(R.id.timeToInjection))))));
        appCompatButton3.perform(click());

        ViewInteraction button5 = onView(
                allOf(withId(R.id.buttonInject),
                        withParent(allOf(withId(R.id.layout_injection),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        button5.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.temperatureCheckBox)));
        appCompatCheckBox.perform(scrollTo(), click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.acheCheckBox)));
        appCompatCheckBox2.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.fragmentInjectionDetailsButton)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.fragmentToInjectionButton),
                        withParent(allOf(withId(R.id.fragment_to_injection),
                                withParent(withId(R.id.timeToInjection))))));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction button6 = onView(
                allOf(withId(R.id.buttonPostpone),
                        withParent(allOf(withId(R.id.layout_injection),
                                withParent(withId(android.R.id.content))))));
        button6.perform(click());

        ViewInteraction button7 = onView(
                allOf(withId(android.R.id.button1)));
        button7.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction imageButton2 = onView(
                allOf(withContentDescription("Close"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton2.perform(click());

        SystemClock.sleep(1000); //wait for menu animation
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
