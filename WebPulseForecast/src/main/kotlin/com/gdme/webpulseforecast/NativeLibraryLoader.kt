package com.gdme.webpulseforecast

import java.io.File

object NativeLibraryLoader {
    fun loadNativeLibrary() {
        val libName = "web_analysis_jni"
        val resourcePath = "/native/windows/$libName.dll"

        try {
            val inputStream = javaClass.getResourceAsStream(resourcePath)
            if (inputStream != null) {
                val tempFile = File.createTempFile(libName, ".dll")
                tempFile.deleteOnExit()

                inputStream.use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                System.load(tempFile.absolutePath)
                println("Successfully loaded native library from: ${tempFile.absolutePath}")
            } else {
                throw IllegalStateException("Native library not found in resources: $resourcePath")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Failed to load native library", e)
        }
    }
}