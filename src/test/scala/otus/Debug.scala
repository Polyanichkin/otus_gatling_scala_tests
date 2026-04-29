package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Debug extends Simulation{

  setUp(
    CommonScenario()           // что делает пользователь

      .inject(atOnceUsers(1))  // как и сколько пользователей запускаем

  ).protocols(otus.httpProtocol.proxy(Proxy("localhost", port = 8888))) // для отладки в Fiddler
//    ).protocols(otus.httpProtocol) // куда и с какими HTTP-настройками бьём

}
