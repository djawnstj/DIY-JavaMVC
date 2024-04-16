plugins {
    id("java")
}

group = "hello"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 서블릿
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet:jstl:1.2")

    // 톰캣
    implementation("org.apache.tomcat.embed:tomcat-embed-core:8.5.42")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:8.5.42")

    // 로그백
    implementation("ch.qos.logback:logback-classic:1.4.7")

    // 테스트
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.25.3")
}

tasks.test {
    useJUnitPlatform()
}
