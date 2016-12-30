package com.karolskora.msorgranizer.activities;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.karolskora.msorgranizer.R;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.hamcrest.Description;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.assertion.ViewAssertions.matches;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class OtherTests {

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
    public void test3(){
                        onView(allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()))
                                .perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(withId(R.id.material_drawer_slider_layout)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("data: 20.10.2016    godzina: 3:36"),
                        childAtPosition(
                                allOf(withId(R.id.injectionsListView),
                                        withParent(withId(R.id.historyLayout))),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        pressBack();
    }
    @Test
    public void test4(){
        ViewInteraction imageButton4 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton4.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(withId(R.id.material_drawer_slider_layout)),
                        isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.dosesFragmentEditText), withText("4"), isDisplayed()));
        appCompatEditText5.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.dosesFragmentEditText), withText("4"), isDisplayed()));
        appCompatEditText6.perform(replaceText("10"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.fragmentReserveButton), withText("zapisz zmiany"), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction editText10 = onView(
                allOf(withId(R.id.dosesFragmentEditText), withText("10"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.drawer_layout),
                                        0),
                                1),
                        isDisplayed()));
        editText10.check(matches(withText("10")));

        ViewInteraction imageButton5 = onView(
                allOf(withContentDescription("Close"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton5.perform(click());
    }

    @Test
    public void test5(){
        ViewInteraction imageButton7 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton7.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(withId(R.id.material_drawer_slider_layout)),
                        isDisplayed()));
        recyclerView3.perform(actionOnItemAtPosition(4, click()));

        ViewInteraction checkBox = onView(
                allOf(withText("data: 20.10.2016    godzina: 3:36"),
                        withParent(withId(R.id.layout_report))));
        checkBox.perform(scrollTo(), click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.fragmentReportButton), withText("generuj raport")));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button2), withText("wyświetl"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton6.perform(click());
    }

    @Test
    public void test6(){
        ViewInteraction imageButton10 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton10.perform(click());

        ViewInteraction recyclerView5 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(withId(R.id.material_drawer_slider_layout)),
                        isDisplayed()));
        recyclerView5.perform(actionOnItemAtPosition(7, click()));

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("ustawienia użytkownika"),
                        childAtPosition(
                                withId(R.id.settingsListView),
                                0)));
        appCompatTextView2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.doctorNameTextEdit), withText("a")));
        appCompatEditText7.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.doctorNameTextEdit), withText("test")));
        appCompatEditText8.perform(pressImeActionButton());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.fragmentUserSettingsButtonSave), withText("zapisz zmiany")));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction editText11 = onView(
                allOf(withId(R.id.doctorNameTextEdit), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.settingsFragmentContainer),
                                        0),
                                3),
                        isDisplayed()));
        editText11.check(matches(withText("test")));

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("ustawienia powiadomień"),
                        childAtPosition(
                                withId(R.id.settingsListView),
                                1)));
        appCompatTextView3.perform(scrollTo(), click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.notificationSettingsButtonSave), withText("zapisz zmiany")));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("ustawienia motywu"),
                        childAtPosition(
                                withId(R.id.settingsListView),
                                2)));
        appCompatTextView4.perform(scrollTo(), click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.image_holo_light_settings),
                        withParent(allOf(withId(R.id.ThemeSettingsGridLayout),
                                withParent(withId(R.id.fragment_theme_settings)))),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.fragmentUThemeSettingsButtonSave), withText("zapisz zmiany"),
                        withParent(allOf(withId(R.id.fragment_theme_settings),
                                withParent(withId(R.id.settingsFragmentContainer))))));
        appCompatButton9.perform(scrollTo(), click());
    }
    @Test
    public void test7(){
        ViewInteraction imageButton11 = onView(
                allOf(withContentDescription("Open"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        imageButton11.perform(click());

        ViewInteraction recyclerView6 = onView(
                allOf(withId(R.id.material_drawer_recycler_view),
                        withParent(withId(R.id.material_drawer_slider_layout)),
                        isDisplayed()));
        recyclerView6.perform(actionOnItemAtPosition(8, click()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.mi_button_next), withContentDescription("Next"), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.mi_button_next), withContentDescription("Next"), isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.mi_button_next), withContentDescription("Next"), isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.mi_button_next), withContentDescription("Next"), isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withId(R.id.mi_button_next), withContentDescription("Next"), isDisplayed()));
        appCompatImageButton5.perform(click());

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


