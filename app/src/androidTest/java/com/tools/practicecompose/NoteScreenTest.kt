package com.tools.practicecompose

import android.content.Context
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import com.tools.practicecompose.di.AppModule
import com.tools.practicecompose.feature.presentation.TestTag
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
    }

    @Test
    fun addNewNotes() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Assert.assertEquals("com.tools.practicecompose", context.packageName)
        composeRule.onNodeWithTag(TestTag.NoteScreen).performTouchInput { swipeRight() }
        composeRule.onNodeWithTag(TestTag.NoteScreenDrawer).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.DrawerAddNoteButton).performClick()
        composeRule.onNodeWithTag(TestTag.EditScreen).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTag.EditScreenTitle).performTextInput("Title1")
        composeRule.onNodeWithTag(TestTag.EditScreenContent).performTextInput("Content1")
        composeRule.onNodeWithContentDescription("Change Read | Write Mode").performClick()
//        composeRule.activity.onBackPressedDispatcher.onBackPressed()
        Espresso.pressBack()
        composeRule.onNodeWithTag(TestTag.NoteList).onChildren().onFirst().performClick()
    }
}