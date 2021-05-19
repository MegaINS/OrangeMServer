ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "ru.megains"

//val lwjglVersion = "3.2.3"

lazy val OrangeMClient = (project in file("."))
        .settings(

            name := "OrangeMServer",

            version := "0.1",
            // resolvers += "jitpack" at "https://jitpack.io",
            //resolvers += "so-releases" at "https://raw.githubusercontent.com/SpinyOwl/repo/releases",
            //resolvers += "so-snapshots" at "https://raw.githubusercontent.com/SpinyOwl/repo/snapshots",
            // resolvers += "ossrh" at "https://oss.sonatype.org/content/repositories/snapshots/",

            libraryDependencies ++= Seq(
                "org.slf4j" % "slf4j-api" % "1.7.25",
                "ch.qos.logback" % "logback-classic" % "1.2.3",
                "org.scala-lang" % "scala-reflect" % "2.13.1",
                "io.netty" % "netty-all" % "4.1.64.Final"

                // "com.github.SpaiR" % "imgui-java" % "v1.76-0.11",
                // "org.liquidengine" % "legui" % "3.0.2-SNAPSHOT",
                // "org.lwjgl" % "lwjgl-nanovg" % lwjglVersion,
                // "com.github.kotlin-graphics.imgui" % "imgui-gl" % "v1.76",
                // "org.lwjgl" % "lwjgl-nanovg" % lwjglVersion classifier "natives-windows" classifier "natives-linux" classifier "natives-macos",

            )
        )
        .aggregate(om)
        .dependsOn(om)



lazy val om = ProjectRef(file("../OrangeM/"), "orangeM")
