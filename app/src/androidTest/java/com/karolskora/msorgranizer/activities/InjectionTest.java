package com.karolskora.msorgranizer.activities;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.karolskora.msorgranizer.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class InjectionTest {

        @Rule
        public ActivityTestRule<MainActivity> mActivityTestRule =
                new ActivityTestRule<>(MainActivity.class);
        @Test
        public void injectionTest() {

           onView(allOf(withId(R.id.fragmentToInjectionButton),
                            withParent(allOf(withId(R.id.fragment_to_injection),
                                    withParent(withId(R.id.timeToInjection)))))).perform(click());
            onView(allOf(withId(R.id.buttonInject),
                            withParent(allOf(withId(R.id.layout_injection),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed())).perform(click());

            onView(allOf(withId(R.id.temperatureCheckBox))).perform(scrollTo(), click());

            onView(allOf(withId(R.id.acheCheckBox))).perform(scrollTo(), click());

            onView(allOf(withId(R.id.fragmentInjectionDetailsButton))).perform(scrollTo(), click());

            onView(allOf(withId(R.id.fragmentToInjectionButton),
                            withParent(allOf(withId(R.id.fragment_to_injection),
                                    withParent(withId(R.id.timeToInjection)))))).
                    perform(scrollTo(), click());

            onView(allOf(withId(R.id.buttonPostpone),
                            withParent(allOf(withId(R.id.layout_injection),
                                    withParent(withId(android.R.id.content)))))).perform(click());

            onView(allOf(withId(android.R.id.button1))).perform(click());

        }
    @Test
    public void test3(){}
    @Test
    public void test4(){}
    @Test
    public void test5(){}
    @Test
    public void test6(){}
    @Test
    public void test7(){}
    @Test
    public void test8(){}
    @Test
    public void test9(){}
    @Test
    public void test10(){}
}


