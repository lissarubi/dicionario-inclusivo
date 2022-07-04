package dicionarioinclusivo.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Application.configureHTTP() {
  val host = environment.config.property("ktor.deployment.CLIENT_URL")
  install(CORS) {
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Delete)
    allowHeader(HttpHeaders.Authorization)
    allowHeader(HttpHeaders.ContentType)
    allowCredentials = true
    allowNonSimpleContentTypes = true
    allowSameOrigin = true
    if (host.getString() == "*") {
      anyHost()
    } else {
      allowHost(host.toString(), listOf("http", "https"))
    }
  }
  install(Compression) {
    gzip { priority = 1.0 }
    deflate {
      priority = 10.0
      minimumSize(1024) // condition
    }
  }
}
