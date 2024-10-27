package com.example.trabalhogrupo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface ItemCompraDao {
    @Insert
    suspend fun salvarItemCompra(item: ItemCompra)

    @Query("SELECT * FROM ItemCompra")
    suspend fun buscarTodosItens(): List<ItemCompra>
    @Update
    suspend fun atualizarItemCompra(item: ItemCompra)

    @Delete
    suspend fun excluirItemCompra(item: ItemCompra)
}