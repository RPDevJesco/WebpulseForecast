package com.gdme.webpulseforecast

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class AnalyzeProjectAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val service = project.getService(WebPulseForecastService::class.java)

        val projectType = service.analyzeProject()
        val estimation = service.estimateResources(projectType)
        val performanceImpact = service.calculatePerformanceImpact(projectType)
        val issues = service.getPotentialIssues(projectType)

        // Display results (this is a simple example, you might want to update the tool window instead)
        val message = """
            Analysis complete!
            Framework: ${projectType.framework}
            HTML Files: ${projectType.htmlFileCount}
            CSS Files: ${projectType.cssFileCount}
            JS Files: ${projectType.jsFileCount}
            Estimated JS Heap Size: ${estimation.jsHeapSize} bytes
            Estimated DOM Content Loaded: ${estimation.domContentLoaded} ms
            Performance Impact: $performanceImpact
            Issues found: ${issues.size}
        """.trimIndent()

        Messages.showInfoMessage(project, message, "WebPulse Forecast Analysis")
    }
}