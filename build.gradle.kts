/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.21")

    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.github.ajalt:clikt:${project.property("clikt")}")
    implementation("io.github.microutils:kotlin-logging:${project.property("kotlinLogging")}")
    implementation("org.jlleitschuh.guice:kotlin-guiced-core:${project.property("kotlinGuiced")}")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.assertj:assertj-core:${project.property("assertj")}")
}

application {
    mainClassName = "bp.bench.AppKt"
}
