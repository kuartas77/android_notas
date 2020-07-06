package com.sofdreamc.notas

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}