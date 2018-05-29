package com.test.finero.finerio.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.test.finero.finerio.R
import com.test.finero.finerio.adapters.MovimientosAdapter
import com.test.finero.finerio.responseObjects.Movimiento
import com.test.finero.finerio.responseObjects.MovimientosResponse
import com.test.finero.finerio.utility.FinerioNetwork
import com.test.finero.finerio.utility.StringUtility
import khronos.Dates
import khronos.days
import khronos.minus
import khronos.toString
import kotlinx.android.synthetic.main.activity_movimientos.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovimientosActivity : AppCompatActivity() {

    var pagination = 10
    var movimientosOnRequest = false

    val queryMap: MutableMap<String, String> = hashMapOf(
            "deep" to "true",
            "includeCharges" to "true",
            "includeDeposits" to "true",
            "max" to "10",
            "tmz" to "GMT-07:00",
            "startDate" to (Dates.today - 30.days).toString(StringUtility.DATE_FORMAT),
            "endDate" to Dates.today.toString(StringUtility.DATE_FORMAT)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimientos)
        setUserName()
        callMovimientos()
        movimientos_progress.visibility = View.VISIBLE
    }

    private fun callMovimientos() {
        movimientosOnRequest = true
        if (intent.extras != null) {
            FinerioNetwork.service.movimientosCall(intent.extras.get(StringUtility.ID_EXTRA).toString(),
                    queryMap,
                    intent.extras.get(StringUtility.AUTH_EXTRA).toString()).enqueue(object : Callback<MovimientosResponse> {
                override fun onFailure(call: Call<MovimientosResponse>?, t: Throwable?) {
                    movimientosOnRequest = false
                    movimientos_progress.visibility = View.GONE
                    Log.d(MovimientosActivity::class.java.simpleName, "fail")
                }

                override fun onResponse(call: Call<MovimientosResponse>?, response: Response<MovimientosResponse>?) {
                    movimientosOnRequest = false
                    movimientos_progress.visibility = View.GONE
                    if (response?.raw()?.code() == 200) {
                        setMovimientoList(response.body()?.movimientos ?: arrayListOf())
                    }
                }
            })
        }

    }

    private fun callMovimientoUpdate(){
        movimientosOnRequest = true
        if (intent.extras != null) {
            FinerioNetwork.service.movimientosCall(intent.extras.get(StringUtility.ID_EXTRA).toString(),
                    queryMap,
                    intent.extras.get(StringUtility.AUTH_EXTRA).toString()).enqueue(object : Callback<MovimientosResponse> {
                override fun onFailure(call: Call<MovimientosResponse>?, t: Throwable?) {
                    movimientosOnRequest = false
                    movimientos_progress.visibility = View.GONE
                    Log.d(MovimientosActivity::class.java.simpleName, "fail")
                }

                override fun onResponse(call: Call<MovimientosResponse>?, response: Response<MovimientosResponse>?) {
                    movimientosOnRequest = false
                    movimientos_progress.visibility = View.GONE
                    if (response?.raw()?.code() == 200) {
//                        setMovimientoList(response.body()?.movimientos ?: arrayListOf())
                    }
                }
            })
        }
    }

    private fun setMovimientoList(movimientos: List<Movimiento>) {
        val linearLayoutManager = LinearLayoutManager(this)
        var adapter = MovimientosAdapter(movimientos)
        list_movimientos.setHasFixedSize(true)
        list_movimientos.layoutManager = linearLayoutManager
        list_movimientos.adapter = adapter
        list_movimientos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView?.layoutManager?.itemCount
                if (totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1 && !movimientosOnRequest) {
                     if(pagination + 10 >= totalItemCount){
                         pagination +=10
                         queryMap["max"] = (pagination).toString()
                         callMovimientoUpdate()
                     }else{

                     }
                }
            }
        })
    }

    private fun setUserName() {
        tv_username.text = if (intent.extras != null) intent.extras.get(StringUtility.LOGIN_EXTRA).toString() else ""
    }

    override fun onBackPressed() {
        alert {
            title = getString(R.string.dialog_text_close_session)
            yesButton { super.onBackPressed() }
            noButton { }
        }.show()
    }
}
