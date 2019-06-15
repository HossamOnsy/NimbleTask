package com.sam.nimbletask.utils




// Made is as a Util Function so later on it would used whenever an Exchanging Rate is required
// Of course we can change currencyRateMapping -> fetch it from Room or the last saved CurrencyConversionList

object AppUtils {
    fun getValueAfterConversion(
        value: Double,
        currencyRateMapping: Map<String, Double>?,
        second: String
    ): Double {
        var result = 0.0
        if (currencyRateMapping != null && currencyRateMapping.get(second) != null)
            result = value * currencyRateMapping.get(second)!!

        return result
    }

   
}