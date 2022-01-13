package hu.benefanlabs.moviebudgets.data.network

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

actual class ApiClient {
    actual fun createClient(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                host = "api.themoviedb.org/3"
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY
            }
        }
    }
}