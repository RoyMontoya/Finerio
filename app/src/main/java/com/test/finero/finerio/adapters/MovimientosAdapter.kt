package com.test.finero.finerio.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.finero.finerio.R
import com.test.finero.finerio.model.Movimiento
import kotlinx.android.synthetic.main.card_movimiento.view.*

class MovimientosAdapter(val movimientos: List<Movimiento>) : RecyclerView.Adapter<MovimientosAdapter.MovimientosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_movimiento, parent, false)
        return MovimientosViewHolder(view)
    }

    override fun getItemCount() = movimientos.size


    override fun onBindViewHolder(holder: MovimientosViewHolder?, position: Int) {
        holder?.let {
            val movimiento = movimientos[position]
            holder.row.row_date.text = movimiento.date
            holder.row.row_description.text = movimiento.description
            holder.row.row_monto.text = movimiento.monto
            holder.row.row_clasificacion.text = movimiento.clasificacion
        }
    }


    class MovimientosViewHolder(val row: View) : RecyclerView.ViewHolder(row)
}