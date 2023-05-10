package com.tools.practicecompose

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.tools.practicecompose.di.AppModule
import com.tools.practicecompose.feature.presentation.Screen
import com.tools.practicecompose.feature.presentation.notes.components.NoteScreen
import com.tools.practicecompose.ui.theme.MainComposeTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            MainComposeTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NoteScreen.route
                ) {
                    composable(route = Screen.NoteScreen.route) {
                        NoteScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun clickOrderSection() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Assert.assertEquals("com.tools.practicecompose", context.packageName)
        composeRule.onNodeWithTag("OrderSection").assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag("OrderSection").assertIsDisplayed()
    }
}