plugins {
    kotlin("jvm") version "1.4.10"
    id("com.ncorti.ktfmt.gradle") version "0.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

ktfmt {
    dropboxStyle()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}
