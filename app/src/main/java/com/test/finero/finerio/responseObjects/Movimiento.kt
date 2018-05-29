package com.test.finero.finerio.responseObjects

import com.google.gson.annotations.SerializedName

data class Movimiento(@SerializedName("amount") val amount: Int,
                      @SerializedName("customDescription") val description: String,
                      @SerializedName("date") val date: String,
                      @SerializedName("concepts") val concepts: List<ConceptsResponse>)