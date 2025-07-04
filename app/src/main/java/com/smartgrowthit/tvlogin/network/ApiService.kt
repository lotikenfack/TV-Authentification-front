package com.smartgrowthit.tvlogin.network

import retrofit2.http.*
import retrofit2.Response

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val token: String)
data class SessionStatusResponse(val valid: Boolean, val reason: String?)
data class LogoutResponse(val success: Boolean)

interface ApiService {
    @POST("/accounts/login/")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/accounts/session/status/")
    suspend fun sessionStatus(@Header("Authorization") token: String): Response<SessionStatusResponse>

    @POST("/accounts/logout/")
    suspend fun logout(@Header("Authorization") token: String): Response<LogoutResponse>
}
