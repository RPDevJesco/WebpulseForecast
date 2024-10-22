package com.gdme.webpulseforecast

class WebPulseForecastNative {
    init {
        NativeLibraryLoader.loadNativeLibrary()
    }

    external fun analyzeProjectType(projectPath: String): ProjectType
    external fun estimateResources(project: ProjectType): ResourceEstimation
    external fun calculatePerformanceImpact(project: ProjectType): Double
    external fun getResourceUsageDisplay(resourceEstimation: ResourceEstimation): String
    external fun getPotentialIssues(project: ProjectType): Array<String>

    data class ProjectType(
        var isReact: Boolean = false,
        var isVue: Boolean = false,
        var isAngular: Boolean = false,
        var isZephyr: Boolean = false,
        var isSvelte: Boolean = false,
        var isSalesforce: Boolean = false,
        var framework: String = "",
        var requiredLibraries: String = "",
        var htmlFileCount: Int = 0,
        var cssFileCount: Int = 0,
        var jsFileCount: Int = 0,
        var jsonFileCount: Int = 0,
        var imageFileCount: Int = 0,
        var reactComponentCount: Int = 0,
        var customElementCount: Int = 0,
        var externalResourceCount: Int = 0,
        var frameworkComponentCount: Int = 0,
        var salesforceMetadataCount: Int = 0,
        var potentialIssueCount: Int = 0,
        var potentialIssues: Array<PotentialIssue> = arrayOf(),
        var jsInfo: JSInfo = JSInfo(),
        var cssInfo: CSSInfo = CSSInfo(),
        var jsonInfo: JSONInfo = JSONInfo(),
        var htmlInfo: HTMLInfo = HTMLInfo()
    )

    data class ResourceEstimation(
        var jsHeapSize: Long = 0,
        var transferredData: Long = 0,
        var resourceSize: Long = 0,
        var domContentLoaded: Int = 0,
        var largestContentfulPaint: Int = 0
    )

    // Other data classes can remain as they are, as they're not directly used in the JNI layer
    // But you might want to keep them for use in your Kotlin code

    data class CustomElement(
        var name: String = "",
        var count: Int = 0
    )

    data class ExternalResource(
        var url: String = "",
        var type: String = "",
        var size: Long = 0
    )

    data class PotentialIssue(
        var description: String = "",
        var location: String = ""
    )

    data class HTMLInfo(
        var tagCount: Int = 0,
        var scriptCount: Int = 0,
        var styleCount: Int = 0,
        var linkCount: Int = 0
    )

    data class CSSInfo(
        var ruleCount: Int = 0,
        var selectorCount: Int = 0,
        var propertyCount: Int = 0,
        var mediaQueryCount: Int = 0,
        var keyframeCount: Int = 0
    )

    data class JSInfo(
        var functionCount: Int = 0,
        var variableCount: Int = 0,
        var classCount: Int = 0,
        var reactComponentCount: Int = 0,
        var vueInstanceCount: Int = 0,
        var angularModuleCount: Int = 0,
        var eventListenerCount: Int = 0,
        var asyncFunctionCount: Int = 0,
        var promiseCount: Int = 0,
        var closureCount: Int = 0
    )

    data class JSONInfo(
        var objectCount: Int = 0,
        var arrayCount: Int = 0,
        var keyCount: Int = 0,
        var maxNestingLevel: Int = 0
    )
}