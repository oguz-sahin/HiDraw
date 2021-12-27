// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://developer.huawei.com/repo/")
    }
    dependencies {
        classpath(ClassPaths.gradle)
        classpath(ClassPaths.kotlin)
        classpath(ClassPaths.hilt)
        classpath(ClassPaths.navigation)
        classpath(ClassPaths.ag_connect)
    }
}



task<Delete>("Delete") {
    delete = setOf(rootProject.buildDir)
}

task<Copy>("installGitHook") {
    from("scripts/pre-commit")
    into(".git/hooks")
    fileMode = 777
}

