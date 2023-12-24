plugins {
    id("kotlin")
}

repositories {
    mavenCentral()
}

dependencies {
    // https://github.com/Z3Prover/z3/releases along with: libz3.dylib and libz3java.dylib
    implementation(rootProject.files("z3/com.microsoft.z3.jar"))

    implementation(project(mapOf("path" to ":extras")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
