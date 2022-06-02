import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.7"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

apply("gradle/jacoco.gradle")

detekt {
    ignoreFailures = true
    config = files("config/detekt/detekt.yml")
}

group = "com.uff.br"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.7")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.xerial:sqlite-jdbc:3.32.3.2")
    implementation("com.github.gwenn:sqlite-dialect:0.1.0")
    implementation("com.google.code.gson:gson:2.9.0")

    testImplementation(group = "io.mockk", name = "mockk", version = "1.9.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.7")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
