package com.paypay.currency_converter.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.paypay.currency_converter.util.Utils
import com.paypay.currency_converter.util.Utils.EMPTY
import com.paypay.currency_converter.util.Utils.roundToTwoDecimalPlaces
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

@Entity(tableName = "table_currency")
data class Currency(@PrimaryKey
                    @ColumnInfo(name = "column_currencyCode")
                    val currencyCode: String,
                    @ColumnInfo(name = "column_exchangeRate")
                    var exchangeRate: Double) {

    @ColumnInfo(name = "column_order")
    var order = Utils.Order.INVALID.position

    @ColumnInfo(name = "column_isSelected")
    var isSelected = false

    @Ignore
     var conversion = Conversion(BigDecimal(exchangeRate))

    @Ignore
    private var decimalFormatter: DecimalFormat

    @Ignore
     var decimalSeparator: String

    init {
        val numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault())
        val conversionPattern = "##0.##"
        decimalFormatter = numberFormatter as DecimalFormat
        decimalFormatter.applyPattern(conversionPattern)
        decimalSeparator = decimalFormatter.decimalFormatSymbols.decimalSeparator.toString()
    }

    /**
     * Currency code without the "USD_" prefix.
     * Example: USD_EUR -> EUR
     */
    val trimmedCurrencyCode
        get() = currencyCode.substring(CURRENCY_CODE_START_INDEX)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Currency
        if (currencyCode != other.currencyCode) return false
        return true
    }

    fun deepEquals(other: Currency): Boolean {
        return (this.currencyCode == other.currencyCode &&
                this.exchangeRate == other.exchangeRate &&
                this.order == other.order)
    }

    override fun hashCode() = currencyCode.hashCode()

    /**
     * Since the toString() method is really only useful for debugging I've structured it in a way
     * which concisely displays the object's state.
     *
     * Example: 4 S* F* EUR
     *          | |  |   |
     *      Order |  |   |
     *     Selected? |   |
     *         Focused?  |
     *            Currency code
     *
     *    *blank if not selected/focused
     */
    override fun toString() = buildString {
        append("{")
        append(order)
        append(" ")
        append(trimmedCurrencyCode)
        append(" ")
        append(" ")
        append("}")
    }

     inner class Conversion(conversionValue: BigDecimal) {
        /**
         * The underlying numeric conversion result.
         * Example: 1234.5678
         */
        var conversionValue: BigDecimal = conversionValue




        /**
         * The [conversionValue] as a String.
         * Example: "1234.5678"
         */
        val conversionString :String
        get() = conversionValue.roundToTwoDecimalPlaces().toString()


        /**
         * The [conversionString] formatted according to locale.
         * Example:    USA: 1,234.5678
         *          France: 1.234,5678
         */
        val conversionText: String
            get() {
                return if (conversionString.isNotBlank()) {
                    formatConversion(conversionString)
                } else {
                    String.EMPTY
                }
            }

        /**
         * The hint displayed when [conversionText] is empty.
         */
        var conversionHint = String.EMPTY
            set(value) {
                field = formatConversion(BigDecimal(value).toString())
            }

        /**
         * Formats a numeric String with grouping separators while retaining trailing zeros.
         */
        private fun formatConversion(conversion: String): String {
            return when {
                conversion.contains(".") -> {
                    val splitConversion = conversion.split(".")
                    val wholePart = splitConversion[Utils.Order.FIRST.position]
                    val decimalPart = splitConversion[Utils.Order.SECOND.position]
                    decimalFormatter.format(BigDecimal(wholePart)) + decimalSeparator + decimalPart
                }
                else -> {
                    decimalFormatter.format(BigDecimal(conversion))
                }
            }
        }
    }

    companion object {
        const val CURRENCY_CODE_START_INDEX = 3
    }
}