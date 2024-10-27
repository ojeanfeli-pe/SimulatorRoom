package com.example.trabalhogrupo

import android.app.Activity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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
    var nomeItem by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var prioridade by remember { mutableStateOf("") }
    var listaItens by remember { mutableStateOf<List<ItemCompra>>(emptyList()) }

    val context = LocalContext.current
    val dbConnection = AppDatabase.getDatabase(context)
    val itemCompraDAO = dbConnection.itemCompraDao()

    LaunchedEffect(Unit) {
        listaItens = itemCompraDAO.buscarTodosItens()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        TextField(value = nomeItem, onValueChange = { nomeItem = it }, label = { Text("Nome do Item") })
        TextField(value = quantidade, onValueChange = { quantidade = it }, label = { Text("Quantidade") })
        TextField(value = prioridade, onValueChange = { prioridade = it }, label = { Text("Prioridade") })

        Button(onClick = {
            if (nomeItem.isNotEmpty() && quantidade.isNotEmpty() && prioridade.isNotEmpty()) {
                val novoItem = ItemCompra(
                    nome = nomeItem,
                    quantidades = quantidade.toInt(),
                    prioridade = prioridade.toInt(),
                )
                CoroutineScope(Dispatchers.IO).launch {
                    itemCompraDAO.salvarItemCompra(novoItem)
                    listaItens = itemCompraDAO.buscarTodosItens()

                    (context as? Activity)?.runOnUiThread {
                        Toast.makeText(context, "Item cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                }
                nomeItem = ""
                quantidade = ""
                prioridade = ""
            } else {
                Toast.makeText(context, "Por favor, preencha todos os campos e selecione um usuário.", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Adicionar Item")
        }

        LazyColumn {
            items(listaItens) { item ->
                Text(text = "${item.nome} - Qtd: ${item.quantidades} - Prioridade: ${item.prioridade}")
                Button(onClick = {

                    CoroutineScope(Dispatchers.IO).launch {

                        itemCompraDAO.excluirItemCompra(item)

                        listaItens = itemCompraDAO.buscarTodosItens()

                        (context as? Activity)?.runOnUiThread{
                            Toast.makeText(context, "item removido", Toast.LENGTH_SHORT).show()
                        }

                    }

                }) {
                    Text(text = "Apagar")
                }

                var isEditing by remember { mutableStateOf(false) }
                var editedPrioridade by remember { mutableStateOf(item.prioridade.toString()) }
                Button(onClick = {
                    isEditing = !isEditing
                }) {
                    Text("Editar")
                }

                if(isEditing){
                    TextField(
                        value = editedPrioridade,
                        onValueChange = { editedPrioridade = it },
                        label = { Text("Prioridade") }
                    )
                    Button(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val updatedItem = item.copy(prioridade = editedPrioridade.toInt())

                            itemCompraDAO.atualizarItemCompra(updatedItem)

                            listaItens = itemCompraDAO.buscarTodosItens()

                            (context as? Activity)?.runOnUiThread {
                                Toast.makeText(context, "item atualizado", Toast.LENGTH_SHORT).show()
                            }
                            isEditing = false

                        }
                    }) { Text(text = "Concluir") }
                }

            }
        }
    }
}
