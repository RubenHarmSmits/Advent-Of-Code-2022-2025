import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.0"
}

application {
    mainClass.set("util.Runner")
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.reflections", "reflections", "0.9.12")
    implementation("org.jgrapht:jgrapht-core:1.5.2")
    implementation("net.objecthunter:exp4j:0.4.8")
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.8.1")
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", "5.8.1")
    testImplementation("org.hamcrest", "hamcrest", "2.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0") // or latest version
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.0")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
