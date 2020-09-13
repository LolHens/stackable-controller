lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "de.lolhens",
  version := "0.7.0-SNAPSHOT",

  scalaVersion := "2.13.3",
  crossScalaVersions := Seq("2.12.12", scalaVersion.value),

  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")),

  homepage := Some(url("https://github.com/LolHens/stackable-controller")),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/LolHens/stackable-controller"),
      "scm:git@github.com:LolHens/stackable-controller.git"
    )
  ),
  developers := List(
    Developer(id = "LolHens", name = "Pierre Kisters", email = "pierrekisters@gmail.com", url = url("https://github.com/LolHens/"))
  ),

  Compile / doc / sources := Seq.empty,

  version := {
    val tagPrefix = "refs/tags/"
    sys.env.get("CI_VERSION").filter(_.startsWith(tagPrefix)).map(_.drop(tagPrefix.length)).getOrElse(version.value)
  },

  publishMavenStyle := true,

  publishTo := sonatypePublishToBundle.value,

  credentials ++= (for {
    username <- sys.env.get("SONATYPE_USERNAME")
    password <- sys.env.get("SONATYPE_PASSWORD")
  } yield Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    username,
    password
  )).toList
)

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
  .enablePlugins(PlayScala)
  .settings(commonSettings)
  .settings(
    publish / skip := true,

    libraryDependencies ++= Seq(
      jdbc,
      guice,
      specs2 % "test",
      "com.typesafe.play" %% "play" % play.core.PlayVersion.current,
      "com.h2database" % "h2" % "1.4.200",
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
