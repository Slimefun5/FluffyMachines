plugins {
    java
    id("com.gradleup.shadow") version "9.3.2"
    id("io.github.intisy.github-gradle") version "1.8.2.1"
}

group = "io.ncbpfluffybear"
description = "FluffyMachines is a Slimefun addon that adds various machines, tools, and utilities."

// Shared Slimefun-addon build conventions (Java 8, spigot-api baseline, core dep, publish, shadow, version).
apply(from = "https://raw.githubusercontent.com/Slimefun5/workflows/stable/slimefun-addon.gradle")

repositories {
    maven("https://jitpack.io")
}

dependencies {
    githubImplementation("Slimefun5:SlimefunMetrics:v1.0.0")
    compileOnly("com.gmail.nossr50.mcMMO:mcMMO:2.1.149") {
        exclude(group = "org.jetbrains", module = "annotations")
        exclude(group = "com.sk89q.worldguard")
    }
    // Shaded (compiled against upstream Slimefun4; repointed to slimefun5 below).
    implementation("com.github.Slimefun-Addon-Community:extrautils:73e76ac06c") {
        isTransitive = false
    }

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.15.2")
    testImplementation("org.slf4j:slf4j-simple:2.0.16")
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:4.107.0") {
        exclude(group = "org.jetbrains", module = "annotations")
    }
}

configurations.testImplementation {
    extendsFrom(configurations.compileOnly.get())
}

tasks {
    shadowJar {
        relocate("org.bstats", "fluffymachines.libs.bstats")
        relocate("dev.j3fftw.extrautils", "io.ncbpfluffybear.fluffymachines.extrautils")
        // Bundled ExtraUtils is compiled against upstream Slimefun4; repoint its API refs to slimefun5.
        relocate("io.github.thebusybiscuit.slimefun4", "io.github.thebusybiscuit.slimefun5")
    }
    compileTestJava { enabled = false }
    test { enabled = false }
}
