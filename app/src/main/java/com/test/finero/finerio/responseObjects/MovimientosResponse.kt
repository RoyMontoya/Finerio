package com.test.finero.finerio.responseObjects

import com.google.gson.annotations.SerializedName

data class MovimientosResponse(@SerializedName("data") val movimientos: List<Movimiento>)