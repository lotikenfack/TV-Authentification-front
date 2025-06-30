package com.smartgrowthit.tvlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.smartgrowthit.tvlogin.network.ApiClient
import com.smartgrowthit.tvlogin.network.LoginRequest
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var loginBtn: Button
    private lateinit var errorMsg: TextView

    companion object {
        fun newInstance(message: String? = null): LoginFragment {
            val fragment = LoginFragment()
            val args = Bundle()
            args.putString("error_message", message)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        loginBtn = view.findViewById(R.id.loginBtn)
        errorMsg = view.findViewById(R.id.errorMsg)

        arguments?.getString("error_message")?.let {
            errorMsg.text = it
            errorMsg.visibility = View.VISIBLE
        }

        loginBtn.setOnClickListener {
            doLogin()
        }
        return view
    }

    private fun doLogin() {
        val user = username.text.toString()
        val pass = password.text.toString()
        errorMsg.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val response = ApiClient.api.login(LoginRequest(user, pass))
                if (response.isSuccessful && response.body() != null) {
                    AuthManager.saveToken(response.body()!!.token)
                    (activity as? MainActivity)?.onLoginSuccess()
                } else {
                    val error = response.errorBody()?.string() ?: "Erreur inconnue"
                    errorMsg.text = when {
                        error.contains("invalid") -> "Identifiants invalides"
                        error.contains("expired") -> "Vos identifiants ont expiré"
                        error.contains("disabled") -> "Compte désactivé"
                        error.contains("time") -> "Votre temps est écoulé"
                        else -> "Erreur : $error"
                    }
                    errorMsg.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                errorMsg.text = "Erreur réseau : ${e.localizedMessage}"
                errorMsg.visibility = View.VISIBLE
            }
        }
    }
}
