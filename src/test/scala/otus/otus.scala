package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

object otus {

  val httpProtocol: HttpProtocolBuilder =
//    http.baseUrl("http://webtours.load-test.ru:1080")
//        .acceptEncodingHeader(value = "gzip, deflate")
//        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
      http.baseUrl("api.vc.ru")
          .acceptEncodingHeader(value = "gzip, deflate, br, zstd")
          .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

}
