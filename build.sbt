resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

lazy val commonSettings = Seq(
// Refine scalac params from tpolecat
  scalacOptions --= Seq(
    "-Xfatal-warnings"
  )
)

lazy val zioDeps = libraryDependencies ++= Seq(
  "dev.zio" %% "zio"          % Version.zio,
  "dev.zio" %% "zio-test"     % Version.zio % "test",
  "dev.zio" %% "zio-test-sbt" % Version.zio % "test"
)

lazy val deps = libraryDependencies ++= Seq(
  "joda-time" % "joda-time"    % Version.joda,
  "com.wix"   %% "accord-core" % Version.accord
)

lazy val esDeps = libraryDependencies ++= Seq(
  "com.sksamuel.elastic4s" %% "elastic4s-core"         % Version.es,
  "com.sksamuel.elastic4s" %% "elastic4s-http-streams" % Version.es,
  "com.sksamuel.elastic4s" %% "elastic4s-embedded"     % Version.es
  // "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % Version.es,
  // "com.sksamuel.elastic4s" %% "elastic4s-testkit"       % Version.es % "test"f
)

lazy val sttpDeps = libraryDependencies ++= Seq(
  "com.softwaremill.sttp.client" %% "core"                          % Version.sttp,
  "com.softwaremill.sttp.client" %% "circe"                         % Version.sttp,
  "com.softwaremill.sttp.client" %% "async-http-client-backend-zio" % Version.sttp,
  "io.circe"                     %% "circe-generic"                 % Version.circe
)

lazy val root = (project in file("."))
  .settings(
    organization := "Neurodyne",
    name := "explore",
    version := "0.0.1",
    scalaVersion := "2.13.1",
    maxErrors := 3,
    commonSettings,
    deps,
    zioDeps,
    esDeps,
    sttpDeps,
    testFrameworks := Seq(new TestFramework("zio.test.sbt.ZTestFramework"))
  )

// Aliases
addCommandAlias("rel", "reload")
addCommandAlias("com", "all compile test:compile it:compile")
addCommandAlias("fmt", "all scalafmtSbt scalafmtAll")
