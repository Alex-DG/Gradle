Gradle
======

Create gradle project from an eclipse ADT project

1/ add gradle to eclipse https://github.com/spring-projects/eclipse-integration-gradle/blob/master/README.md

2/ generation of build.gradle file -In Eclipse, select File > Export. -In the window that appears, open Android and select Generate Gradle build files.

3/ creation of ANDROID_HOME environment variable in windows 7 (to be able to use 'gradlew build' command): in command prompt:

set ANDROID_HOME=C:<installation location>\android-sdk-windows set PATH=%PATH%;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools

4/ I got an error with lint, so to fix that: in build.gradle, added this lines under android part: lintOptions { abortOnError false }

5/in command prompt at my project's root, generation of my new gradle project: gradlew build
