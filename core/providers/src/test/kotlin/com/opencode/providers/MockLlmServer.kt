package com.opencode.providers

import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

class MockLlmServer {
  private var engine: ApplicationEngine? = null

  fun start(): Int {
    val server =
        embeddedServer(
            Netty,
            port = 0,
        ) {
      routing {
        get("/v1/chat") {
          call.respondText(replay("chat_stream_001").joinToString(""), ContentType.Text.EventStream)
        }
        get("/v1/responses") {
          call.respondText(replay("responses_001").joinToString(""), ContentType.Text.EventStream)
        }
      }
    }
    server.start(wait = false)
    engine = server
    return runBlocking { server.resolvedConnectors() }.first().port
  }

  fun replay(name: String): List<String> {
    val path = "llm/$name.json"
    val stream = requireNotNull(javaClass.classLoader.getResourceAsStream(path)) { "missing fixture: $path" }
    val text = stream.bufferedReader(Charsets.UTF_8).readText()
    return Json.parseToJsonElement(text).jsonArray.map { it.jsonPrimitive.content }
  }

  fun stop() {
    engine?.stop(0, 0)
    engine = null
  }
}
