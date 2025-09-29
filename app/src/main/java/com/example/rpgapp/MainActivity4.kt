package com.example.rpgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Personagem

class MainActivity4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recebe os dados via extras
        val nome = intent.getStringExtra("NOME") ?: ""
        val raca = intent.getStringExtra("RACA")?.let { enums.Raca.valueOf(it) } ?: enums.Raca.HUMANO
        val classe = intent.getStringExtra("CLASSE")?.let { enums.Classe.valueOf(it) } ?: enums.Classe.GUERREIRO
        val estilo = intent.getStringExtra("ESTILO")?.let { enums.EstiloRolagem.valueOf(it) } ?: enums.EstiloRolagem.CLASSICO
        val atributos = model.Atributos(
            intent.getIntExtra("FORCA", 0),
            intent.getIntExtra("DESTREZA", 0),
            intent.getIntExtra("CONSTITUICAO", 0),
            intent.getIntExtra("INTELIGENCIA", 0),
            intent.getIntExtra("SABEDORIA", 0),
            intent.getIntExtra("CARISMA", 0)
        )
        val personagem = model.Personagem(nome, raca, classe, estilo, atributos)

        setContent {
            androidx.compose.material3.MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FichaPersonagem(personagem)
                }
            }
        }
    }
}

@Composable
fun FichaPersonagem(p: Personagem) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scroll)
    ) {
        Text("Ficha do Personagem", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nome: ${p.nome}", style = MaterialTheme.typography.titleMedium)
                Text("Raça: ${p.raca.name}", style = MaterialTheme.typography.bodyMedium)
                Text("Movimento: ${p.raca.movimento}", style = MaterialTheme.typography.bodyMedium)
                Text("Infravisão: ${p.raca.infravisao ?: "Nenhuma"}", style = MaterialTheme.typography.bodyMedium)
                Text("Alinhamento: ${p.raca.alinhamento}", style = MaterialTheme.typography.bodyMedium)
                Text("Habilidades da Raça: ${p.raca.habilidades.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Classe: ${p.classe.name}", style = MaterialTheme.typography.titleMedium)
                Text("Armas: ${p.classe.armas.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
                Text("Armaduras: ${p.classe.armaduras.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
                Text("Itens Mágicos: ${p.classe.itensMagicos.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
                Text("Habilidades da Classe: ${p.classe.habilidadesClasse.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
                Text("Estilo de Rolagem: ${p.estiloRolagem.name}", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Atributos", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Força:", style = MaterialTheme.typography.bodyMedium)
                    Text(p.atributos.forca.toString(), style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Destreza:", style = MaterialTheme.typography.bodyMedium)
                    Text(p.atributos.destreza.toString(), style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Constituição:", style = MaterialTheme.typography.bodyMedium)
                    Text(p.atributos.constituicao.toString(), style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Inteligência:", style = MaterialTheme.typography.bodyMedium)
                    Text(p.atributos.inteligencia.toString(), style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Sabedoria:", style = MaterialTheme.typography.bodyMedium)
                    Text(p.atributos.sabedoria.toString(), style = MaterialTheme.typography.bodyMedium)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Carisma:", style = MaterialTheme.typography.bodyMedium)
                    Text(p.atributos.carisma.toString(), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
