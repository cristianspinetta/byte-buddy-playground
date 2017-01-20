
import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly._

object Assembly {

  lazy val settings: Seq[Setting[_]] = Seq(
    assemblyJarName in assembly := s"agent.jar",
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(cacheUnzip = false),
    packageOptions <+= (name, version, organization) map { (title, version, vendor) =>
        Package.ManifestAttributes(
          "Created-By" -> "Simple Build Tool",
          "Built-By" -> System.getProperty("user.name"),
          "Build-Jdk" -> System.getProperty("java.version"),
          "Specification-Title" -> title,
          "Specification-Version" -> version,
          "Specification-Vendor" -> vendor,
          "Implementation-Title" -> title,
          "Implementation-Version" -> version,
          "Implementation-Vendor-Id" -> vendor,
          "Implementation-Vendor" -> vendor,
          "Premain-Class" -> "agent.AgentEntryPoint",
          "Agent-Class" -> "agent.AgentEntryPoint",
          "Can-Redefine-Classes" -> "true",
          "Can-Set-Native-Method-Prefix" -> "true",
          "Can-Retransform-Classes" -> "true")
    },
    assemblyShadeRules in assembly := Seq(
        ShadeRule.rename("net.bytebuddy.**" -> "agent.libs.@0").inAll,
        ShadeRule.rename("com.typesafe.config.**" -> "agent.libs.@0").inAll
    )
  ) ++ addArtifact(artifact in(Compile, assembly), assembly) ++ (test in assembly := {}) ++ assemblyArtifact

  lazy val assemblyArtifact: Setting[Artifact] = artifact in (Compile, assembly) := {
    val art = (artifact in (Compile, assembly)).value
    art.copy(`classifier` = Some("assembly"))
  }

  lazy val notAggregateInAssembly = Seq(aggregate in assembly := false)
  lazy val excludeScalaLib = Seq(assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false))
}