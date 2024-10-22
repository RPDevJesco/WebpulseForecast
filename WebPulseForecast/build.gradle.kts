plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.gdme"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.6")
    type.set("IU") // Ultimate platform includes support for all IDEs

    // Add required plugins for different IDE support
    plugins.set(listOf(
        "JavaScript",           // Required for WebStorm support
        "com.intellij.css",    // CSS support
        "org.jetbrains.plugins.node-remote-interpreter", // Node.js support
        "com.intellij.java",   // Java support
        "org.jetbrains.kotlin" // Kotlin support
    ))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }

    prepareSandbox {
        from("src/main/resources/native/windows") {
            into("${intellij.pluginName.get()}/lib/native/windows")
        }
    }

    runIde {
        // Add IDE-specific system properties if needed
        systemProperties["idea.platform.prefix"] = "all"
        jvmArgs("-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005")
    }
}