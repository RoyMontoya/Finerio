package com.test.finero.finerio.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.test.finero.finerio.R
import com.test.finero.finerio.adapters.MovimientosAdapter
import com.test.finero.finerio.model.Movimiento
import com.test.finero.finerio.utility.StringUtility
import kotlinx.android.synthetic.main.activity_movimientos.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MovimientosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movimientos)
        setUserName()
        setMovimientoList()
    }

    private fun setMovimientoList() {
        list_movimientos.setHasFixedSize(true)
        list_movimientos.layoutManager = LinearLayoutManager(this)
        list_movimientos.adapter = MovimientosAdapter(createDummyList())
    }

    private fun setUserName() {
        tv_username.text = if (intent.extras != null) intent.extras.get(StringUtility.LOGIN_EXTRA).toString()
        else ""
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
