package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StepwiseLoadSimulation extends Simulation {

  private val MaxRps: Double = 500.0

  private val StepRps      = MaxRps * 0.1
  private val StepsCount   = 10
  private val StepDuration = 2.minutes
  private val HttpConf     = otus.httpProtocol

  setUp(
    CommonScenario()
      .inject(
        incrementUsersPerSec(StepRps)
          .times(StepsCount)
          .eachLevelLasting(StepDuration)
          .separatedByRampsLasting(0.seconds)
          .startingFrom(StepRps)
      )
      .protocols(HttpConf)
  ).maxDuration(StepsCount * StepDuration + 30.seconds)
}