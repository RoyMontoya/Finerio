package com.test.finero.finerio.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("username") val username: String,
                         @SerializedName("token_type") val tokenType: String,
                         @SerializedName("access_token") val AccessToken: String)