package com.abdelrahman.financetracker.currencyconverter.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ExchangeRateDto(
    @SerializedName("base")
    val baseCurrency: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)

data class ExchangeRateApiResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Map<String, Double>
)

data class ConversionDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("query")
    val query: QueryDto,
    @SerializedName("info")
    val info: InfoDto,
    @SerializedName("historical")
    val historical: Boolean,
    @SerializedName("date")
    val date: String,
    @SerializedName("result")
    val result: Double
)

data class QueryDto(
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String,
    @SerializedName("amount")
    val amount: Double
)

data class InfoDto(
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("rate")
    val rate: Double
) 