package hu.benefanlabs.moviebudgets.data.network

import io.ktor.client.*

expect class ApiClient() {
    fun createClient(): HttpClient
}