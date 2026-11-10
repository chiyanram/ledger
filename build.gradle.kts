import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    java
    groovy
    alias(libs.plugins.springBoot)
}

group = "dev.chiyanram"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(27) }
}

repositories { mavenCentral() }

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useSpock(libs.versions.spock)
            dependencies {
                implementation(platform(SpringBootPlugin.BOM_COORDINATES))
                implementation("org.apache.groovy:groovy")
            }
        }
        // every IT lives here: it starts PostgreSQL in a container, so it never runs with the unit tests
        register<JvmTestSuite>("integrationTest") {
            useSpock(libs.versions.spock)
            dependencies {
                implementation(project())
                implementation(platform(SpringBootPlugin.BOM_COORDINATES))
                implementation("org.apache.groovy:groovy")
                implementation(libs.spockSpring)
                implementation("org.springframework.boot:spring-boot-starter-test")
                implementation("org.springframework.boot:spring-boot-starter-jdbc")
                implementation("org.springframework.boot:spring-boot-testcontainers")
                implementation("org.testcontainers:testcontainers-postgresql")
            }
            targets.all { testTask.configure { shouldRunAfter(tasks.named("test")) } }
        }
    }
}

tasks.named("check") { dependsOn(testing.suites.named("integrationTest")) }
