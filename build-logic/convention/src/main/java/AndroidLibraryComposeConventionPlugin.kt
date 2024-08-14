import com.android.build.api.dsl.LibraryExtension
import com.palone.convention.ExtensionType
import com.palone.convention.configureAndroidCompose
import com.palone.convention.configureBuildTypes
import com.palone.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            pluginManager.run{
                apply("runique.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")

            }
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }
}