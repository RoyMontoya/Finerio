package com.test.finero.finerio.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.test.finero.finerio.R
import com.test.finero.finerio.responseObjects.LoginResponse
import com.test.finero.finerio.responseObjects.MeResponse
import com.test.finero.finerio.utility.FinerioNetwork
import com.test.finero.finerio.utility.StringUtility
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_username.setText("roymontoya89@gmail.com")
        et_password.setText("R208201349")
    }


    fun loginRequest(view: View) {
        val username = if (et_username.text.isEmpty()) "" else et_username.text.toString()
        val password = if (et_password.text.isEmpty()) "" else et_password.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            val loginBody: HashMap<String, String> = hashMapOf(
                    "username" to username,
                    "password" to password
            )
            progressbar.visibility = View.VISIBLE
            callLogin(loginBody)
        }
    }

    private fun callLogin(loginBody: HashMap<String, String>) {
        FinerioNetwork.service.loginCall(loginBody).enqueue(object : Callback<LoginResponse> {

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                progressbar.visibility = View.GONE
                longToast(t?.message.toString())
            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                if (response?.raw()?.code() == 200) {
                    callMe(response.body()?.tokenType + " " + response.body()?.AccessToken, response.body()?.username ?: "")
                } else {
                    progressbar.visibility = View.GONE
                    longToast("Usuario o password Incorrecto")
                }
            }
        })
    }

    private fun callMe(auth: String, username: String) {
        FinerioNetwork.service.meCall(auth).enqueue(object : Callback<MeResponse> {
            override fun onFailure(call: Call<MeResponse>?, t: Throwable?) {
                progressbar.visibility = View.GONE
            }

            override fun onResponse(call: Call<MeResponse>?, response: Response<MeResponse>?) {
                progressbar.visibility = View.GONE
                if (response?.raw()?.code() == 200) {
                    goToMovimientosActivity(response.body()?.id ?: "", auth, username)
                }
            }
        })
    }

    private fun goToMovimientosActivity(userId: String, auth: String, username: String) {
        longToast("Inicio de Sesion Exitoso")
        val intent = Intent(this, MovimientosActivity::class.java)
        intent.putExtra(StringUtility.ID_EXTRA, userId)
        intent.putExtra(StringUtility.AUTH_EXTRA, auth)
        intent.putExtra(StringUtility.LOGIN_EXTRA, username)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
