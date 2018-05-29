package com.test.finero.finerio.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.test.finero.finerio.R
import com.test.finero.finerio.adapters.MovimientosAdapter
import com.test.finero.finerio.model.Movimiento
import com.test.finero.finerio.responses.MovimientosResponse
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
            FinerioNetwork.service.movimientosCall(query,
                    intent.extras.get(StringUtility.ID_EXTRA).toString(),
                    intent.extras.get(StringUtility.AUTH_EXTRA).toString()).enqueue(object : Callback<MovimientosResponse>{
                override fun onFailure(call: Call<MovimientosResponse>?, t: Throwable?) {
                    Log.d(MovimientosActivity::class.java.simpleName, "fail")
                }

                override fun onResponse(call: Call<MovimientosResponse>?, response: Response<MovimientosResponse>?) {
                    Log.d(MovimientosActivity::class.java.simpleName, "success")
                }

            })

        }


    }

    private fun setMovimientoList() {
        list_movimientos.setHasFixedSize(true)
        list_movimientos.layoutManager = LinearLayoutManager(this)
        list_movimientos.adapter = MovimientosAdapter(createDummyList())
    }

    private fun setUserName() {
        tv_username.text = if (intent.extras != null) intent.extras.get(StringUtility.LOGIN_EXTRA).toString() else ""
    }

    private fun createDummyList(): List<Movimiento> {
        return listOf(
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST"),
                Movimiento("MAY28", "TEST", "100", "TEST")
        )
    }


    override fun onBackPressed() {
        alert {
            title = "Deseas cerrar sesion?"
            yesButton { super.onBackPressed() }
            noButton { }
        }.show()
    }
}
