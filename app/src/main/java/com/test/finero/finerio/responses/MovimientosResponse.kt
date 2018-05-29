package com.test.finero.finerio.responses

import com.google.gson.annotations.SerializedName

data class MovimientosResponse(@SerializedName("data") val movimientos: List<SingleMovimiento>)