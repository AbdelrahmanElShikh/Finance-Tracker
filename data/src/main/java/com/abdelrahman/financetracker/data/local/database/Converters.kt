package com.abdelrahman.financetracker.data.local.database

import androidx.room.TypeConverter
import com.abdelrahman.financetracker.domain.model.TransactionType

class Converters {
    
    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }
    
    @TypeConverter
    fun toTransactionType(type: String): TransactionType {
        return TransactionType.valueOf(type)
    }
} 