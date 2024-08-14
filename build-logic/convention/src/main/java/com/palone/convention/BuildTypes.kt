package com.palone.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *>,
    extensionType: ExtensionType
){
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir).getProperty("API_KEY")
        when(extensionType){
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension>{
                    buildTypes {
                        release{
                           configureReleaseBuildType(commonExtension,apiKey)
                        }
                        debug{

                            configureDebugBuildType(apiKey)
                        }
                    }
                }
            }
            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension>{
                    buildTypes {
                        release{
                         configureReleaseBuildType(commonExtension,apiKey)
                        }
                        debug{
                            configureDebugBuildType(apiKey)

                        }
                    }
                }
            }
        }
    }
}
private fun BuildType.configureDebugBuildType(apiKey:String){
    buildConfigField("String","API_KEY","\"$apiKey\"")
    buildConfigField("String","BASE_URL","\"https://runique.pl-coding.com:8080\"")
}
private fun BuildType.configureReleaseBuildType(commonExtension: CommonExtension<*, *, *, *, *>,apiKey:String){
    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )

    buildConfigField("String","API_KEY","\"$apiKey\"")
    buildConfigField("String","BASE_URL","\"https://runique.pl-coding.com:8080\"")
}