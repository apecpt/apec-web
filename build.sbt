name := """apec-bib-web"""

version := "0.1"

val godivaVersion = "0.1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "pt.org.apec" %% "apec-books-common" % "0.1",
  "pt.org.apec" %% "apec-users-common" % "0.1",
  "com.ruiandrebatista.godiva" %% "godiva-core" % godivaVersion,
  "com.ruiandrebatista.godiva" %% "godiva-play-json" % godivaVersion,
  cache,
  ws,
  filters,
  specs2 % Test,
    "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "bootstrap" % "3.3.5",
  "org.webjars" % "html5shiv" % "3.7.3",
  "org.webjars.bower" % "bootstrapaccessibilityplugin" % "1.0.4",
  "org.webjars" % "handlebars" % "4.0.2",
  "org.webjars" % "requirejs-domready" % "2.0.1-2",
    "com.adrianhurt" %% "play-bootstrap3" % "0.4.5-P24",
    "org.scalaz" %% "scalaz-core" % "7.1.5"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += Resolver.bintrayRepo("batista", "maven")

pipelineStages := Seq(rjs, digest, gzip)


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

dockerBaseImage := "java:8"
