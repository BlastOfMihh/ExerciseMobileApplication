package org.example.cross_unstabilizer.Networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.delete
import io.ktor.client.request.put
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.example.cross_unstabilizer.Model.Exercise

class BackendClient {
    private val httpClient: HttpClient = createHttpClient(OkHttp.create())
    private val socketClient: SocketClient
    private val url = "http://localhost:5000"
    private val host = "localhost"
    private val port = 5000

    init {
        socketClient= SocketClient("http://localhost:5000")
    }

    suspend fun connectSocket(socketToCallback:Map<String, (Array<Any>) -> Unit> = mapOf()){
        socketClient.connect(socketToCallback)
    }

    suspend fun getAllExercises(): List<Exercise> {
        val response = try {
            httpClient.get(
                urlString = "$url/exercises"
            ) {
                header("Content-Type", "application/json")
            }
        } catch (e: Exception) {
            throw e
        }
        return response.body()
    }

    suspend fun remove(id: Int) {
        try {
            httpClient.delete(
                urlString = "$url/exercise/$id"
            ) {
                header("Content-Type", "application/json")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun update(
        id: Int,
        name: String,
        difficulty: Int,
        description: String,
        progress: Int
    ) {
        try {
            httpClient.put(
                urlString = "$url/exercise/$id"
            ) {
                header("Content-Type", "application/json")
                setBody(
                    Exercise(
                        id = id,
                        name = name,
                        difficulty = difficulty,
                        description = description,
                        progress = progress
                    )
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun create(
        name: String,
        difficulty: Int,
        description: String,
        progress: Int
    ) {
        try {
            httpClient.post(
                urlString = "$url/exercise"
            ) {
                header("Content-Type", "application/json")
                setBody(
                    Exercise(
                        name = name,
                        difficulty = difficulty,
                        description = description,
                        progress = progress
                    )
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getById(id: Int): Exercise {
        val response = try {
            httpClient.get(
                urlString = "$url/exercise/$id"
            ) {
                header("Content-Type", "application/json")
            }
        } catch (e: Exception) {
            throw e
        }
        return response.body<Exercise>()
    }

    suspend fun ping(): Boolean {
        val response = try {
            httpClient.get(
                urlString = "$url/"
            ) {
                header("Content-Type", "application/json")
            }
        } catch (e: Exception) {
            throw e
        }
        return response.body<String>()=="Backend server is running!"
    }
}