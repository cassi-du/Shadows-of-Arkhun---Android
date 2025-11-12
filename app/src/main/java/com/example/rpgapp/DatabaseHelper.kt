package com.example.rpgapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.Atributos
import model.Personagem
import enums.Classe
import enums.Raca
import enums.EstiloRolagem

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "personagens.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "personagens"
        private const val COL_ID = "id"
        private const val COL_NOME = "nome"
        private const val COL_RACA = "raca"
        private const val COL_CLASSE = "classe"
        private const val COL_ESTILO = "estilo"
        private const val COL_FORCA = "forca"
        private const val COL_DEX = "destreza"
        private const val COL_CONS = "constituicao"
        private const val COL_INT = "inteligencia"
        private const val COL_SAB = "sabedoria"
        private const val COL_CAR = "carisma"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val create = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOME TEXT NOT NULL,
                $COL_RACA TEXT,
                $COL_CLASSE TEXT,
                $COL_ESTILO TEXT,
                $COL_FORCA INTEGER,
                $COL_DEX INTEGER,
                $COL_CONS INTEGER,
                $COL_INT INTEGER,
                $COL_SAB INTEGER,
                $COL_CAR INTEGER
            )
        """.trimIndent()
        db.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun createPersonagem(p: Personagem): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NOME, p.nome)
            put(COL_RACA, p.raca.name)
            put(COL_CLASSE, p.classe.name)
            put(COL_ESTILO, p.estiloRolagem.name)
            put(COL_FORCA, p.atributos.forca)
            put(COL_DEX, p.atributos.destreza)
            put(COL_CONS, p.atributos.constituicao)
            put(COL_INT, p.atributos.inteligencia)
            put(COL_SAB, p.atributos.sabedoria)
            put(COL_CAR, p.atributos.carisma)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun characterExists(name: String): Boolean {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT 1 FROM $TABLE_NAME WHERE $COL_NOME = ? LIMIT 1", arrayOf(name))
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    fun getAllPersonagens(): List<Personagem> {
        val list = mutableListOf<Personagem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COL_NOME", null)
        if (cursor.moveToFirst()) {
            do {
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOME))
                val racaStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_RACA)) ?: "HUMANO"
                val classeStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_CLASSE)) ?: "GUERREIRO"
                val estiloStr = cursor.getString(cursor.getColumnIndexOrThrow(COL_ESTILO)) ?: "CLASSICO"
                val forca = cursor.getInt(cursor.getColumnIndexOrThrow(COL_FORCA))
                val destreza = cursor.getInt(cursor.getColumnIndexOrThrow(COL_DEX))
                val constituicao = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CONS))
                val inteligencia = cursor.getInt(cursor.getColumnIndexOrThrow(COL_INT))
                val sabedoria = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SAB))
                val carisma = cursor.getInt(cursor.getColumnIndexOrThrow(COL_CAR))

                val raca = try { Raca.valueOf(racaStr) } catch (e: Exception) { Raca.HUMANO }
                val classe = try { Classe.valueOf(classeStr) } catch (e: Exception) { Classe.GUERREIRO }
                val estilo = try { EstiloRolagem.valueOf(estiloStr) } catch (e: Exception) { EstiloRolagem.CLASSICO }

                val atributos = Atributos(forca, destreza, constituicao, inteligencia, sabedoria, carisma)
                val p = Personagem(nome, raca, classe, estilo, atributos)
                list.add(p)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }
}
