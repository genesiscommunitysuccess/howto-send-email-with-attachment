dependencies {
    implementation("global.genesis:file-server-api:${project.ext["fileServerVersion"]}")
    implementation("global.genesis:genesis-notify-api:${properties["notifyVersion"]}")
    compileOnly(genesis("script-dependencies"))
    genesisGeneratedCode(withTestDependency = true)
    testImplementation(genesis("dbtest"))
    testImplementation(genesis("testsupport"))
    testImplementation(genesis("pal-eventhandler"))
}

description = "howto-send-email-with-attachment-app"

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources", "src/main/genesis")
        }
    }
}
