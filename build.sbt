lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "de.lolhens",
  version := "0.7.0-SNAPSHOT",

  scalaVersion := "2.13.3",
  crossScalaVersions := Seq("2.12.12", scalaVersion.value)
)

/*resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "sonatype releases" at "http://oss.sonatype.org/content/repositories/releases"
)*/

lazy val core = project
  .settings(commonSettings)
  .settings(
    name := "stackable-controller",

    scalacOptions ++= Seq("-unchecked"),

    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % play.core.PlayVersion.current % "provided",

    )
  )

lazy val sample = project
  .enablePlugins(play.sbt.PlayScala)
  .settings(commonSettings)
  .settings(
    publish / skip := true,

    libraryDependencies ++= Seq(
      jdbc,
      guice,
      specs2 % "test",
      "com.typesafe.play" %% "play" % play.core.PlayVersion.current,
      //"com.h2database" % "h2" % "1.4.196",
      "org.scalikejdbc" %% "scalikejdbc" % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-config" % "3.5.0",
      "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5",
      "org.slf4j" % "slf4j-simple" % "[1.7,)"
    )
  )
  .dependsOn(core)

lazy val root = project.in(file("."))
  .settings(commonSettings)
  .settings(
    publish / skip := true
  )
  .aggregate(core, sample)
