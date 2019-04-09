/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")
    application
}

repositories {
    jcenter()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.property("kotlinxCoroutines")}")
    implementation("com.github.ajalt:clikt:${project.property("clikt")}")
    implementation("io.github.microutils:kotlin-logging:${project.property("kotlinLogging")}")
    implementation("org.jlleitschuh.guice:kotlin-guiced-core:${project.property("kotlinGuiced")}")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.assertj:assertj-core:${project.property("assertj")}")
}

application {
    mainClassName = "com.spectralogic.bp.bench.AppKt"
}
