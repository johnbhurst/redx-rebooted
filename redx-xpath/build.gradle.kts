// Copyright 2022 John Hurst
// John Hurst
// 2022-01-04

description = "description"

plugins {
    kotlin("jvm")
    groovy
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("jaxen:jaxen:1.2.0")
    testImplementation("org.codehaus.groovy:groovy-xml:3.0.8")
    testImplementation("org.spockframework:spock-core:2.0-groovy-3.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
