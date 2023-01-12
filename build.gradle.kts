plugins{
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("java-library")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    mavenLocal()
}



dependencies {
    compileOnly ("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    compileOnly ("com.github.MilkBowl:VaultAPI:1.7")
    implementation ("team.unnamed:inject:1.0.1")
    implementation ("me.fixeddev:commandflow-universal:0.5.3")
    implementation ("me.fixeddev:commandflow-bukkit:0.5.2")
}


tasks {
    shadowJar {
        archiveFileName.set("RankInc.jar")

        relocate("me.fixeddev", "${project.group}.rankinc.libs.commandflow")
        relocate("team.unnamed.inject", "${project.group}.rankinc.libs.inject")

    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
