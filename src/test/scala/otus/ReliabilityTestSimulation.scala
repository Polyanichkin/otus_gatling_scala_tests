package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class ReliabilityTestSimulation extends Simulation {


  private val StableMaxRps: Double = 3.5
  private val TargetRps: Double = StableMaxRps * 0.8
  private val RampUpDuration: FiniteDuration = 15.minutes
  private val TestDuration: FiniteDuration = 1.hour
  private val RampDownDuration: FiniteDuration = 5.minutes
  private val HttpConf = otus.httpProtocol

  setUp(
    CommonScenario()
      .inject(
        rampUsersPerSec(0).to(TargetRps).during(RampUpDuration),
        constantUsersPerSec(TargetRps).during(TestDuration),
        rampUsersPerSec(TargetRps).to(0).during(RampDownDuration)
      )
      .protocols(HttpConf)
  )
    .assertions(
      global.failedRequests.percent.lte(0.5),
      global.responseTime.percentile(95).lte(3000),
      global.responseTime.percentile(99).lte(5000)
    )
    .maxDuration(TestDuration + RampUpDuration + RampDownDuration)
}