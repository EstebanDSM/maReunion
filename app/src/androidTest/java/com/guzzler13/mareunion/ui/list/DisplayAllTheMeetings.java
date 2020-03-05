package com.guzzler13.mareunion.ui.list;


import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.guzzler13.mareunion.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DisplayAllTheMeetings {

    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityTestRule = new ActivityTestRule<>(MeetingListActivity.class);


    @Test
    public void displayAllTheMeetings() {

        onView(ViewMatchers.withId(R.id.list_meetings))
                .check(matches(hasChildCount(7)));

    }
}
