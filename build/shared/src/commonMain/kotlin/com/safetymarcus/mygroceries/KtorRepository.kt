package com.safetymarcus.mygroceries

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Base class for all Ktor repositories. Provides protected get/put/post/delete methods
 * that automatically prepend the BASE_URL and use a shared HttpClient.
 */
abstract class KtorRepository(
    private val baseUrl: String = getBaseUrl(),
    private val httpClient: HttpClient = sharedHttpClient
) {
    companion object {
        val sharedHttpClient: HttpClient by lazy {
            HttpClient {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            }
        }
    }

    protected suspend fun get(path: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse =
        httpClient.get("$baseUrl$path", block)
    protected suspend fun post(path: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse =
        httpClient.post("$baseUrl$path", block)
    protected suspend fun put(path: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse =
        httpClient.put("$baseUrl$path", block)
    protected suspend fun delete(path: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse =
        httpClient.delete("$baseUrl$path", block)
}
