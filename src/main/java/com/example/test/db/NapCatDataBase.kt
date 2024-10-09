package com.example.test.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Defina a migração fora da classe NapCatDataBase
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Exemplo de alteração no banco
        db.execSQL("ALTER TABLE produto ADD COLUMN nova_coluna TEXT")
    }
}

@Database(entities = [Produto::class, Carrinho::class], version = 2, exportSchema = false)
abstract class NapCatDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun carrinhoDao(): CarrinhoDao
}