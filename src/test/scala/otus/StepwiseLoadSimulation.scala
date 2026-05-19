package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StepwiseLoadSimulation extends Simulation {

  private val TargetOpsPerHour: Double = 6000.0
  private val TargetMaxRps: Double = TargetOpsPerHour / 3600.0

  private val StepRps      = TargetMaxRps * 0.1
  private val StepsCount   = 10
  private val StepDuration = 3.minutes
  private val HttpConf     = otus.httpProtocol

  setUp(
    CommonScenario()
      .inject(
        incrementUsersPerSec(StepRps)
          .times(StepsCount)
          .eachLevelLasting(StepDuration)
          .separatedByRampsLasting(10.seconds)
          .startingFrom(StepRps)
      )
      .protocols(HttpConf)
    // .assertions(
    //   global.responseTime.percentile95.lte(2000),
    //   global.failedRequests.percent.lte(5)
    // )
  ).maxDuration(31.minutes)
}