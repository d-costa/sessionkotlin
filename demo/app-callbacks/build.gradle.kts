plugins {
    kotlin("jvm") version "1.6.20"
    application
    id("com.github.d-costa.sessionkotlin.plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":protocols"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
}

application {
    // Define the main class for the application.
    mainClass.set("demo.AppKt")
}

/**
 * Note:
 *
 * Comment everything below this if you want to run the app without generating the protocol
 *
 */

tasks.register("copyGenerated", Copy::class.java) {
    dependsOn(":protocols:run")
    from("../protocols/build/generated")
    into("build/generated")
}

tasks.compileKotlin {
    dependsOn("copyGenerated")
}

kotlin.sourceSets.main {
    kotlin.srcDirs(
        file("$buildDir/generated/sessionkotlin/main/kotlin"),
    )
}