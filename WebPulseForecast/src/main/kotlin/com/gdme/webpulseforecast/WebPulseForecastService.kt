package com.gdme.webpulseforecast
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class WebPulseForecastService(private val project: Project) {
    private val nativeInterface: WebPulseForecastNative

    init {
        try {
            nativeInterface = WebPulseForecastNative()
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Failed to initialize WebPulseForecastNative", e)
        }
    }

    fun analyzeProject(): WebPulseForecastNative.ProjectType {
        val projectPath = project.basePath
        if (projectPath.isNullOrEmpty()) {
            println("Project path is null or empty")
            return WebPulseForecastNative.ProjectType()
        }
        try {
            println("Analyzing project at path: $projectPath")
            return nativeInterface.analyzeProjectType(projectPath)
        } catch (e: UnsatisfiedLinkError) {
            println("Failed to call native method: ${e.message}")
            e.printStackTrace()
            return WebPulseForecastNative.ProjectType()
        }
    }

    fun estimateResources(projectType: WebPulseForecastNative.ProjectType): WebPulseForecastNative.ResourceEstimation {
        return nativeInterface.estimateResources(projectType)
    }

    fun calculatePerformanceImpact(projectType: WebPulseForecastNative.ProjectType): Double {
        return nativeInterface.calculatePerformanceImpact(projectType)
    }

    fun getResourceUsageDisplay(resourceEstimation: WebPulseForecastNative.ResourceEstimation): String {
        return nativeInterface.getResourceUsageDisplay(resourceEstimation)
    }

    fun getPotentialIssues(projectType: WebPulseForecastNative.ProjectType): Array<String> {
        return nativeInterface.getPotentialIssues(projectType)
    }
}