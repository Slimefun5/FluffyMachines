plugins {
    java
    id("com.gradleup.shadow") version "9.3.2"
    id("io.github.intisy.github-gradle") version "1.8.2.1"
}

group = "io.ncbpfluffybear"
description = "FluffyMachines is a Slimefun addon that adds various machines, tools, and utilities."

apply(from = "https://raw.githubusercontent.com/Slimefun5/gradle/stable/slimefun-addon.gradle")

dependencies {
<<<<<<< HEAD
    implementation("com.github.Slimefun5:SlimefunMetrics:master-SNAPSHOT")
<<<<<<< HEAD
    compileOnly("io.papermc.paper:paper-api:${property("paperApiVersion")}")
=======
=======
    githubImplementation("Slimefun5:SlimefunMetrics:v1.0.0")
<<<<<<< HEAD
>>>>>>> origin/experimental
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
>>>>>>> origin/experimental
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    githubCompileOnly("Slimefun5:Slimefun5:gh-v5.2.3.2")
=======
>>>>>>> origin/experimental
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
<<<<<<< HEAD
        archiveFileName.set("FluffyMachines v${project.version}.jar")
=======
        relocate("org.bstats", "fluffymachines.libs.bstats")
<<<<<<< HEAD
<<<<<<< HEAD
        archiveFileName.set("FluffyMachines-1.0.0-UNOFFICIAL.jar")
>>>>>>> origin/experimental
=======
        archiveFileName.set("FluffyMachines-$displayVersion.jar")
<<<<<<< HEAD
>>>>>>> origin/experimental
                relocate("dev.j3fftw.extrautils", "io.ncbpfluffybear.fluffymachines.extrautils")
=======
=======
>>>>>>> origin/experimental
        relocate("dev.j3fftw.extrautils", "io.ncbpfluffybear.fluffymachines.extrautils")
        relocate("io.github.thebusybiscuit.slimefun4", "io.github.thebusybiscuit.slimefun5")
<<<<<<< HEAD
>>>>>>> origin/experimental
        exclude("META-INF/**")
    }
    build {
        dependsOn(shadowJar)
    }
    compileTestJava {
        enabled = false
    }
    test {
        enabled = false
=======
>>>>>>> origin/experimental
    }
}
