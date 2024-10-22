package com.gdme.webpulseforecast

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class WebPulseForecastPlugin : ProjectActivity {
    override suspend fun execute(project: Project) {
        // Initialize your plugin here
        // This method is called for each project after startup

        // Setup any listeners or perform any initialization logic
    }
}