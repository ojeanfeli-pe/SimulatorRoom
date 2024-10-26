package com.example.trabalhogrupo

import androidx.room.PrimaryKey

data class ItemCompra (
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val nome: String,
    val quantidades: Int,
    val prioridade: Int,
    val usuarioId: Int
){
}