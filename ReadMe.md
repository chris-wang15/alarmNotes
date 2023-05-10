## What is this project
-- This project is a note application used for practice Jetpack Compose.
-- This application can add and watch notes, and set notifications for notes.
-- This application will use Room as a local database, and use Data Store for preference storage.
-- Clean architecture, coroutine flow, dagger hilt will be used.
-- A local video file (/video.mp4) shows the usage of this application.

## About the features
-- Note List
Here you can watch all your notes, every note has importance level and notification time.<br>
After sliding right, there will be a drawer with three buttons for add new notes, enter
edit level page, and choose sort method, respectively.<br>

-- Edit Page
This page is used to read or edit the note information. Notification time can be setup here.<br>
A floating button is used to switch between read mode and write mode.<br>

-- Level Page
Every note level has a representative color, there is a color picker dialog for user-defined colors.
