package dicionarioinclusivo.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Application.configureHTTP() {
  val host = environment.config.propertyOrNull("ktor.deployment.port") ?: "*"
  install(CORS) {
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Delete)
    allowHeader(HttpHeaders.Authorization)
    allowHeader(HttpHeaders.ContentType)
    allowCredentials = true
    allowNonSimpleContentTypes = true
    allowHost(host.toString())
  }
  install(Compression) {
    gzip { priority = 1.0 }
    deflate {
      priority = 10.0
      minimumSize(1024) // condition
    }
  }
}
