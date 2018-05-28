package com.test.finero.finerio

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.test.finero.finerio.activities.MovimientosActivity
import com.test.finero.finerio.utility.StringUtility
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }


    fun loginRequest(view: View){
         val username = if(et_username.text.isEmpty()) "" else et_username.text
         val password = if(et_password.text.isEmpty()) "" else et_password.text

        if(username.isNotEmpty() && password.isNotEmpty()){
            var intent = Intent(this, MovimientosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(StringUtility.USER_EXTRA, username)
            startActivity(intent)
        }
    }
}
