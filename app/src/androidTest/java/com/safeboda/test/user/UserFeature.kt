package com.safeboda.test.user

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.safeboda.test.R
import com.safeboda.test.core.launchFragmentInHiltContainer
import com.safeboda.test.user.di.UserModule
import com.safeboda.test.user.presentation.ui.SearchFragment
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
@UninstallModules(UserModule::class)
@HiltAndroidTest
class UserFeature {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun showErrorWhenSearchInputEmpty() {
        launchFragmentInHiltContainer<SearchFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.fabSearch)).perform(ViewActions.click())
        BaristaVisibilityAssertions.assertContains(R.string.type_name_first)
    }

}