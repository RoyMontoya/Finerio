package com.test.finero.finerio.responses

import com.google.gson.annotations.SerializedName
import java.io.FileDescriptor

data class SingleMovimiento(@SerializedName("amount") val amount: Int,
                            @SerializedName("customDescription") val description: String)