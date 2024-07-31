pluginManagement {
	repositories {
		maven("https://repo.polyfrost.org/releases")
		mavenCentral()
		gradlePluginPortal()
	}
}

listOf(
	"1.20.1-fabric",
	"1.20.4-fabric",
	"1.20.6-fabric",
	"1.21-fabric"
).forEach { version ->
	include(":$version")
	project(":$version").apply {
		projectDir = file("versions/$version")
		buildFileName = "../../build.gradle.kts"
	}
}

rootProject.buildFileName = "root.gradle.kts"