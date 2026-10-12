lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .settings(
    name         := "play-java-ebean-querybeans-example",
    scalaVersion := "2.13.16",
    libraryDependencies ++= Seq(
      guice,
      "com.h2database" % "h2"                  % "2.5.252",
      // Generates the query beans, in the version of the Ebean of Play Ebean 8.5.0
      "io.ebean"       % "querybean-generator" % "17.1.0" % "provided"
    ),
    // Write the generated query beans to the folder of generated sources instead of the classes folder
    Compile / javacOptions ++= {
      val dir = (Compile / sourceManaged).value
      IO.createDirectory(dir)
      Seq("-s", dir.getAbsolutePath)
    },
    // Since JDK 23, javac runs annotation processors only when asked to
    Compile / javacOptions += "-proc:full",
    // Enhance the code that uses query beans too
    Compile / playEbeanModels ++= Seq("controllers.*"),
    // Scaladoc can't see the generated query beans, so don't generate API docs of the application
    Compile / doc / sources                := Seq.empty,
    Compile / packageDoc / publishArtifact := false
  )
