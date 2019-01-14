import play.sbt.routes.RoutesKeys
resolvers += Resolver.jcenterRepo

name := """play-scala-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SwaggerPlugin)

scalaVersion := "2.12.7"
scalacOptions += "-Ypartial-unification"

libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.3.1" % "provided"
libraryDependencies += "org.reactivemongo" % "play2-reactivemongo_2.12" % "0.16.0-play26"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.5.0"
libraryDependencies += "org.webjars" % "swagger-ui" % "3.20.3"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.23.4" % Test

RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
swaggerDomainNameSpaces := Seq("models")