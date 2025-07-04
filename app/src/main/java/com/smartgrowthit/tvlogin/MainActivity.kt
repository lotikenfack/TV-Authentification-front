package com.smartgrowthit.tvlogin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {
    private val inactivityTimeout = 30 * 60 * 1000L // 30 minutes
    private val handler = Handler(Looper.getMainLooper())
    private val inactivityRunnable = Runnable { forceLogout("Déconnexion pour inactivité") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthManager.init(this)
        if (!AuthManager.isLoggedIn()) {
            showLogin()
        } else {
            finishAndGoHome()
        }
    }

    private fun showLogin(message: String? = null) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, LoginFragment.newInstance(message))
            .commit()
    }

    fun onLoginSuccess() {
        startService(Intent(this, FloatingLogoutService::class.java))
        finishAndGoHome()
    }

    fun forceLogout(reason: String) {
        AuthManager.clearToken()
        AuthManager.clearAccessToken()
        stopService(Intent(this, FloatingLogoutService::class.java))
        showLogin(reason)
    }

    private fun finishAndGoHome() {
        moveTaskToBack(true)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        handler.removeCallbacks(inactivityRunnable)
        handler.postDelayed(inactivityRunnable, inactivityTimeout)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(inactivityRunnable, inactivityTimeout)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(inactivityRunnable)
    }
}
