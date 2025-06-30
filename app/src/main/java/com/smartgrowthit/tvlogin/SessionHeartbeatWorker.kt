package com.smartgrowthit.tvlogin

import android.content.Context
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.smartgrowthit.tvlogin.network.ApiClient

class SessionHeartbeatWorker(ctx: Context, params: WorkerParameters) :
    CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val token = AuthManager.getToken() ?: return Result.success()
        val response = ApiClient.api.sessionStatus("Bearer $token")
        if (!response.isSuccessful || response.body()?.valid == false) {
            AuthManager.clearToken()
            // Ramener l'app au premier plan
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("error_message", response.body()?.reason ?: "Session invalide")
            applicationContext.startActivity(intent)
        }
        return Result.success()
    }

    companion object {
        const val WORK_NAME = "SessionHeartbeatWorker"
    }
}
