package com.example.trabalhogrupo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ItemCompra")
data class ItemCompra (
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val nome: String,
    val quantidades: Int,
    val prioridade: Int,
){
}