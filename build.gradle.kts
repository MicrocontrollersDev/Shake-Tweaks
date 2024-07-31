import org.polyfrost.gradle.util.noServerRunConfigs

plugins {
	id(libs.plugins.pgt.main.get().pluginId)
	id(libs.plugins.pgt.defaults.repo.get().pluginId)
	id(libs.plugins.pgt.defaults.loom.get().pluginId)
	id(libs.plugins.pgt.defaults.java.get().pluginId)
	alias(libs.plugins.blossom)
	id(libs.plugins.shadow.get().pluginId)
	alias(libs.plugins.kotlin) apply false
}

version = project.properties["mod_version"] as String
group = project.properties["maven_group"] as String

repositories {
	mavenCentral()
	maven("https://repo.polyfrost.org/releases")
	maven("https://maven.isxander.dev/releases")
	maven("https://maven.terraformersmc.com/releases")
}

java {
	toolchain {
		when(platform.mcVersion) {
			in 12001..12004 -> {
				languageVersion.set(JavaLanguageVersion.of(17))
			}
			else -> {
				languageVersion.set(JavaLanguageVersion.of(21))
			}
		}
	}
}

loom {
	noServerRunConfigs()
}

dependencies {
	val modMenuVersion = when(platform.mcVersion) {
		12001 -> "7.2.2"
		12004 -> "9.2.0-beta.2"
		12006 -> "10.0.0-beta.1"
		else -> "11.0.0-beta.1"
	}

	val fabricApiVersion = when(platform.mcVersion) {
		12001 -> "0.92.1"
		12004 -> "0.97.0"
		12006 -> "0.98.0"
		else -> "0.100.1"
	}

	val yaclVersion = when(platform.mcVersion) {
		12001 -> "3.4.2+1.20.1-fabric"
		12004 -> "3.4.2+1.20.4-fabric"
		12006 -> "3.4.2+1.20.5-fabric"
		else -> "3.5.0+1.21-fabric"
	}

	modImplementation("com.terraformersmc:modmenu:${modMenuVersion}")
	modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricApiVersion}+${platform.mcVersionStr}")
	modImplementation("dev.isxander:yet-another-config-lib:${yaclVersion}")

	// DevAuth. Lets us log in with our own Minecraft account.
	modRuntimeOnly("me.djtheredstoner:DevAuth-${platform.loaderStr}:1.2.0")
}

tasks.processResources {
	filesMatching("fabric.mod.json") {
		expand(mapOf("version" to project.version))
	}
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${project.base.archivesName.get()}" }
	}
}

configurations.all {
	resolutionStrategy {
		force("net.fabricmc:fabric-loader:0.15.11")
	}
}