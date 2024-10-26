package com.example.trabalhogrupo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface ItemCompraDao {
    @Insert
    suspend fun salvarItemCompra(item: ItemCompra)

    @Query("SELECT * FROM ItemCompra WHERE usuarioId = :usuarioId")
    suspend fun buscarItensPorUsuario(usuarioId: Int): List<ItemCompra>

    @Update
    suspend fun atualizarItemCompra(item: ItemCompra)

    @Delete
    suspend fun excluirItemCompra(item: ItemCompra)
}