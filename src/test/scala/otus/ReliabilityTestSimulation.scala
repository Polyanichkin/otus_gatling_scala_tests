package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class ReliabilityTestSimulation extends Simulation {


  private val MaxRps: Double = 500.0

  private val TargetRps    = MaxRps * 0.8
  private val TestDuration = 1.hour
  private val HttpConf     = otus.httpProtocol

  setUp(
    CommonScenario()
      .inject(
        constantUsersPerSec(TargetRps).during(TestDuration)
      )
      .protocols(HttpConf)
  ).maxDuration(TestDuration + 5.minutes)
}