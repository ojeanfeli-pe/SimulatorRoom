package com.example.trabalhogrupo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GerenciarCompras()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GerenciarCompras() {
    // Variáveis de estado para interação com o usuário
    var nomeItem by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var prioridade by remember { mutableStateOf("") }
    var usuarioSelecionado by remember { mutableStateOf<Usuario?>(null) }

    // Lista de itens de compra e usuários
    var listaItens by remember { mutableStateOf<List<ItemCompra>>(emptyList()) }
    var listaUsuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }

    val context = LocalContext.current
    val dbConnection = AppDatabase.getDatabase(context)
    val itemCompraDAO = dbConnection.itemCompraDao()
    val usuarioDAO = dbConnection.usuarioDao()

    LaunchedEffect(Unit) {
        // Carregar usuários e itens do banco de dados ao iniciar
        listaUsuarios = usuarioDAO.buscarTodos()
        if (usuarioSelecionado != null) {
            listaItens = itemCompraDAO.buscarItensPorUsuario(usuarioSelecionado!!.id)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Campo para selecionar ou cadastrar usuário (exemplo simplificado)
        TextField(
            value = usuarioSelecionado?.nome ?: "",
            onValueChange = { /* Aqui você pode implementar a lógica para selecionar um usuário */ },
            label = { Text("Usuário Selecionado") }
        )

        // Campos para adicionar item à lista de compras
        TextField(value = nomeItem, onValueChange = { nomeItem = it }, label = { Text("Nome do Item") })
        TextField(value = quantidade, onValueChange = { quantidade = it }, label = { Text("Quantidade") })
        TextField(value = prioridade, onValueChange = { prioridade = it }, label = { Text("Prioridade") })

        Button(onClick = {
            if (usuarioSelecionado != null && nomeItem.isNotEmpty() && quantidade.isNotEmpty() && prioridade.isNotEmpty()) {
                val novoItem = ItemCompra(
                    nome = nomeItem,
                    quantidades = quantidade.toInt(),
                    prioridade = prioridade.toInt(),
                    usuarioId = usuarioSelecionado!!.id
                )
                CoroutineScope(Dispatchers.IO).launch {
                    itemCompraDAO.salvarItemCompra(novoItem)
                    listaItens += novoItem // Atualiza a lista localmente após a inserção

                    // Exibir mensagem de sucesso no contexto da UI
                    Toast.makeText(context, "Item cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                }
                // Limpar campos após a inserção
                nomeItem = ""
                quantidade = ""
                prioridade = ""
            } else {
                Toast.makeText(context, "Por favor, preencha todos os campos e selecione um usuário.", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Adicionar Item")
        }

        // Exibir lista de itens cadastrados
        LazyColumn {
            items(listaItens) { item ->
                Text(text = "${item.nome} - Qtd: ${item.quantidades} - Prioridade: ${item.prioridade}")
            }
        }
    }
}