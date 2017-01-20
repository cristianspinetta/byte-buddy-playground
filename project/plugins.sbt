resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Kamon Repository Snapshots" at "http://snapshots.kamon.io"

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")
