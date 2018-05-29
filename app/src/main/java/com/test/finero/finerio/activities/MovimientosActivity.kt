package com.test.finero.finerio.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.test.finero.finerio.R
import com.test.finero.finerio.adapters.MovimientosAdapter
import com.test.finero.finerio.responseObjects.MovimientosResponse
import com.test.finero.finerio.responseObjects.Movimiento
import com.test.finero.finerio.utility.FinerioNetwork
import com.test.finero.finerio.utility.StringUtility
import kotlinx.android.synthetic.main.activity_movimientos.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovimientosActivity : AppCompatActivity() {
    val query: MutableMap<String, String> = hashMapOf(
            "deep" to "true",
            "includeCharges" to "true",
            "includeDeposits" to "true",
            "tmz" to "GMT-07:00",
            "startDate" to "2018-05-01",
            "endDate" to "2018-05-28"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimientos)
        setUserName()
//        setMovimientoList()
        callMovimientos()
    }

    private fun callMovimientos() {
        if (intent.extras != null) {
            FinerioNetwork.service.movimientosCall(intent.extras.get(StringUtility.ID_EXTRA).toString(),
                    query,
                    intent.extras.get(StringUtility.AUTH_EXTRA).toString()).enqueue(object : Callback<MovimientosResponse> {
                override fun onFailure(call: Call<MovimientosResponse>?, t: Throwable?) {
                    Log.d(MovimientosActivity::class.java.simpleName, "fail")
                }

                override fun onResponse(call: Call<MovimientosResponse>?, response: Response<MovimientosResponse>?) {
                    if (response?.raw()?.code() == 200) {
                        setMovimientoList(response.body()?.movimientos ?: arrayListOf())
                    }
                }
            })
        }

    }

    private fun setMovimientoList(movimientos: List<Movimiento>) {
        list_movimientos.setHasFixedSize(true)
        list_movimientos.layoutManager = LinearLayoutManager(this)
        list_movimientos.adapter = MovimientosAdapter(movimientos)
    }

    private fun setUserName() {
        tv_username.text = if (intent.extras != null) intent.extras.get(StringUtility.LOGIN_EXTRA).toString() else ""
    }

    override fun onBackPressed() {
        alert {
            title = "Deseas cerrar sesion?"
            yesButton { super.onBackPressed() }
            noButton { }
        }.show()
    }
}
