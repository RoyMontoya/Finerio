package com.test.finero.finerio.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
    var movimientos: MutableList<Movimiento> = mutableListOf()
    var adapter = MovimientosAdapter(movimientos)

    val queryMap: MutableMap<String, String> = hashMapOf(
            "deep" to "true",
            "includeCharges" to "true",
            "includeDeposits" to "true",
            "max" to pagination.toString(),
            "tmz" to "GMT-07:00",
            "startDate" to (Dates.today - 30.days).toString(StringUtility.DATE_FORMAT),
            "endDate" to Dates.today.toString(StringUtility.DATE_FORMAT)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimientos)
        setUserName()
        callMovimientos(false)
    }

    private fun callMovimientos(isUpdating: Boolean) {
        movimientos_progress.visibility = View.VISIBLE
        movimientosOnRequest = true
        if (intent.extras != null) {
            FinerioNetwork.service.movimientosCall(intent.extras.get(StringUtility.ID_EXTRA).toString(),
                    queryMap,
                    intent.extras.get(StringUtility.AUTH_EXTRA).toString()).enqueue(object : Callback<MovimientosResponse> {
                override fun onFailure(call: Call<MovimientosResponse>?, t: Throwable?) {
                    movimientosOnRequest = false
                    movimientos_progress.visibility = View.GONE
                }

                override fun onResponse(call: Call<MovimientosResponse>?, response: Response<MovimientosResponse>?) {
                    movimientosOnRequest = false
                    movimientos_progress.visibility = View.GONE
                    if (response?.raw()?.code() == 200 && movimientos.size < response.body()?.movimientos?.size ?: 0) {
                        if (isUpdating) {
                            movimientos.clear()
                            movimientos.addAll(response.body()?.movimientos ?: mutableListOf())
                            list_movimientos.adapter.notifyDataSetChanged()
                        } else {
                            movimientos.addAll(response.body()?.movimientos ?: mutableListOf())
                            setMovimientoList()
                        }
                    }
                }
            })
        }

    }

    private fun setMovimientoList() {
        val linearLayoutManager = LinearLayoutManager(this)
        list_movimientos.setHasFixedSize(true)
        list_movimientos.layoutManager = linearLayoutManager
        list_movimientos.adapter = adapter
        list_movimientos.adapter.notifyDataSetChanged()
        list_movimientos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView?.layoutManager?.itemCount
                if (totalItemCount == linearLayoutManager.findLastVisibleItemPosition() + 1 && !movimientosOnRequest) {
                    pagination += 10
                    queryMap["max"] = (pagination).toString()
                    callMovimientos(true)
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