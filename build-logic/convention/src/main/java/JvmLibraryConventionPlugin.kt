import com.android.build.api.dsl.LibraryExtension
import com.palone.convention.ExtensionType
import com.palone.convention.configureBuildTypes
import com.palone.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class JvmLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            pluginManager.run{
                apply("org.jetbrains.kotlin.jvm")
            }


        }
    }
}