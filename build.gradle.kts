plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("javax.xml.bind:jaxb-api:2.3.1")
    implementation ("org.glassfish.jaxb:jaxb-runtime:2.3.3")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("org.apache.commons:commons-csv:1.8")
}

tasks.test {
    useJUnitPlatform()
}