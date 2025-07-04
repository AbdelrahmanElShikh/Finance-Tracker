package com.abdelrahman.financetracker.currencyconverter.domain.model

data class Currency(
    val code: String,
    val name: String,
    val symbol: String? = null,
    val flag: String? = null
) {
    companion object {
        fun getAllCurrencies(): List<Currency> {
            return CurrencyConstants.SUPPORTED_CURRENCIES
        }
    }
}

// Common currencies
object CurrencyConstants {
    val SUPPORTED_CURRENCIES = listOf(
        Currency("USD", "US Dollar", "$", "🇺🇸"),
        Currency("EUR", "Euro", "€", "🇪🇺"),
        Currency("GBP", "British Pound", "£", "🇬🇧"),
        Currency("JPY", "Japanese Yen", "¥", "🇯🇵"),
        Currency("CNY", "Chinese Yuan", "¥", "🇨🇳"),
        Currency("CAD", "Canadian Dollar", "C$", "🇨🇦"),
        Currency("AUD", "Australian Dollar", "A$", "🇦🇺"),
        Currency("CHF", "Swiss Franc", "Fr", "🇨🇭"),
        Currency("SEK", "Swedish Krona", "kr", "🇸🇪"),
        Currency("NOK", "Norwegian Krone", "kr", "🇳🇴"),
        Currency("DKK", "Danish Krone", "kr", "🇩🇰"),
        Currency("PLN", "Polish Zloty", "zł", "🇵🇱"),
        Currency("CZK", "Czech Koruna", "Kč", "🇨🇿"),
        Currency("HUF", "Hungarian Forint", "Ft", "🇭🇺"),
        Currency("RON", "Romanian Leu", "lei", "🇷🇴"),
        Currency("BGN", "Bulgarian Lev", "лв", "🇧🇬"),
        Currency("HRK", "Croatian Kuna", "kn", "🇭🇷"),
        Currency("RUB", "Russian Ruble", "₽", "🇷🇺"),
        Currency("TRY", "Turkish Lira", "₺", "🇹🇷"),
        Currency("INR", "Indian Rupee", "₹", "🇮🇳"),
        Currency("KRW", "South Korean Won", "₩", "🇰🇷"),
        Currency("SGD", "Singapore Dollar", "S$", "🇸🇬"),
        Currency("HKD", "Hong Kong Dollar", "HK$", "🇭🇰"),
        Currency("MXN", "Mexican Peso", "$", "🇲🇽"),
        Currency("BRL", "Brazilian Real", "R$", "🇧🇷"),
        Currency("AED", "UAE Dirham", "د.إ", "🇦🇪"),
        Currency("SAR", "Saudi Riyal", "﷼", "🇸🇦"),
        Currency("QAR", "Qatari Riyal", "﷼", "🇶🇦"),
        Currency("KWD", "Kuwaiti Dinar", "د.ك", "🇰🇼"),
        Currency("BHD", "Bahraini Dinar", "د.ب", "🇧🇭"),
        Currency("EGP", "Egyptian Pound", "£", "🇪🇬"),
        Currency("ZAR", "South African Rand", "R", "🇿🇦"),
        Currency("NZD", "New Zealand Dollar", "NZ$", "🇳🇿"),
        Currency("THB", "Thai Baht", "฿", "🇹🇭"),
        Currency("MYR", "Malaysian Ringgit", "RM", "🇲🇾"),
        Currency("IDR", "Indonesian Rupiah", "Rp", "🇮🇩"),
        Currency("PHP", "Philippine Peso", "₱", "🇵🇭"),
        Currency("VND", "Vietnamese Dong", "₫", "🇻🇳")
    )
} 