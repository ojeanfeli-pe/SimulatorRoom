package com.example.trabalhogrupo

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface UsuarioDao {

    @Insert
    suspend fun salvarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario")
    suspend fun buscarTodos(): List<Usuario>

    @Update
    suspend fun atualizarUsuarios(usuario: Usuario)

    @Delete
    suspend fun excluirUsuario(usuario: Usuario)
}