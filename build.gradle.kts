plugins {
    java
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"
}

group = "com.expansemc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://repo-new.spongepowered.org/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")

    implementation("org.jetbrains.exposed:exposed-core:0.27.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.27.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.27.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.27.1")

    implementation("com.h2database:h2:1.4.196")

    compileOnly("org.spongepowered:spongeapi:8.0.0-SNAPSHOT")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}