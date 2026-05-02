import io.gatling.core.Predef._
import io.gatling.core.feeder.FileBasedFeederBuilder
import io.gatling.jdbc.Predef._
import io.gatling.http.Predef._




object Feeders {

  val users: FileBasedFeederBuilder[String] = csv("users.csv").circular

}
