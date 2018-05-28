package com.test.finero.finerio

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.finero.finerio.model.Movimiento
import com.test.finero.finerio.R

class MovimientosAdapter(val movimientos: List<Movimiento>) : RecyclerView.Adapter<MovimientosAdapter.MovimientosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_movimiento, parent, false)
        return MovimientosViewHolder(view)
    }

    override fun getItemCount() = movimientos.size


    override fun onBindViewHolder(holder: MovimientosViewHolder?, position: Int) {

    }


    class MovimientosViewHolder(val card: View) : RecyclerView.ViewHolder(card)
}