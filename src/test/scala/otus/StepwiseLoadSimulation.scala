package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StepwiseLoadSimulation extends Simulation {

  private val BaseOpsPerHour: Double = 6000.0
  private val LoadMultiplier: Double = 10.0

  private val TargetOpsPerHour: Double = BaseOpsPerHour * LoadMultiplier
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
  ).protocols(HttpConf)
    .assertions(
      global.failedRequests.percent.lte(5),
      global.responseTime.percentile(95.0).lte(3000)
    )
    .maxDuration(32.minutes)
}