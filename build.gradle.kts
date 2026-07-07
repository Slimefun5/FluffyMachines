plugins {
    java
    id("com.gradleup.shadow") version "9.3.2"
    id("io.github.intisy.github-gradle") version "1.8.2.1"
}

group = "io.ncbpfluffybear"
description = "FluffyMachines is a Slimefun addon that adds various machines, tools, and utilities."

apply(from = "https://raw.githubusercontent.com/Slimefun5/gradle/stable/slimefun-addon.gradle")

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
}

tasks {
    shadowJar {
        relocate("org.bstats", "fluffymachines.libs.bstats")
        relocate("dev.j3fftw.extrautils", "io.ncbpfluffybear.fluffymachines.extrautils")
        relocate("io.github.thebusybiscuit.slimefun4", "io.github.thebusybiscuit.slimefun5")
    }
}
