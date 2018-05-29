package com.test.finero.finerio.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.test.finero.finerio.R
import com.test.finero.finerio.responses.LoginResponse
import com.test.finero.finerio.utility.FinerioNetwork
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), Callback<LoginResponse> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
            FinerioNetwork.service.LoginCall(loginBody).enqueue(this)
        }
    }

    override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
        progressbar.visibility = View.GONE
        longToast(t?.message.toString())
    }

    override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
        progressbar.visibility = View.GONE
        if (response?.raw()?.code() == 200) {
            goToMovimientosActivity()
        } else {
            longToast("Usuario o password Incorrecto")
        }
    }

    private fun goToMovimientosActivity() {
        longToast("Inicio de Sesion Exitoso")
        val intent = Intent(this, MovimientosActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
