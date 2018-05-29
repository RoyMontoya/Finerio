package com.test.finero.finerio.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.finero.finerio.R
import com.test.finero.finerio.responseObjects.Movimiento
import kotlinx.android.synthetic.main.row_movimiento.view.*
import java.text.DateFormatSymbols

class MovimientosAdapter(val movimientos: List<Movimiento>) : RecyclerView.Adapter<MovimientosAdapter.MovimientosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movimiento, parent, false)
        return MovimientosViewHolder(view)
    }

    override fun getItemCount() = movimientos.size

    override fun onBindViewHolder(holder: MovimientosViewHolder?, position: Int) {
        holder?.let {
            val movimiento = movimientos[position]
            holder.row.row_date.text = finerioFormat(movimiento.date)
            holder.row.row_description.text = movimiento.description
            holder.row.row_monto.text = movimiento.amount.toString()
            holder.row.row_clasificacion.text = movimiento.concepts[0].categoty?.name
        }
    }

    class MovimientosViewHolder(val row: View) : RecyclerView.ViewHolder(row)

    private fun finerioFormat(date: String): String {
        return date.substring(5, 10).replace("-", " ")
                .replaceRange(0..2, DateFormatSymbols()
                        .shortMonths[date.substring(5, 7).toInt() - 1].toUpperCase())
    }

}


