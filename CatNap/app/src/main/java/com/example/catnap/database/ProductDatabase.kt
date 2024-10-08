package com.example.catnap.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import com.example.catnap.R
import com.example.catnap.database.UserDatabase.Companion.COLUMN_BIRTHDATE
import com.example.catnap.database.UserDatabase.Companion.COLUMN_EMAIL
import com.example.catnap.database.UserDatabase.Companion.COLUMN_NAME
import com.example.catnap.database.UserDatabase.Companion.COLUMN_PASSWORD
import com.example.catnap.database.UserDatabase.Companion.COLUMN_PHONE
import com.example.catnap.database.UserDatabase.Companion.TABLE_NAME
import java.io.ByteArrayOutputStream

class ProductDatabase(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "product.db"
        const val DATABASE_VERSION = 1

        const val TABLE_PRODUCTS = "products"
        const val COLUMN_PRODUCT_ID = "id"
        const val COLUMN_PRODUCT_NAME = "name"
        const val COLUMN_PRODUCT_DESC = "descricao"
        const val COLUMN_PRODUCT_PRICE = "preco"
        const val COLUMN_PRODUCT_PHOTO = "foto"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela de produtos
        val createProductTable = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PRODUCT_NAME TEXT NOT NULL,
                $COLUMN_PRODUCT_DESC TEXT NOT NULL,
                $COLUMN_PRODUCT_PRICE TEXT NOT NULL,
                $COLUMN_PRODUCT_PHOTO BLOB
            )
        """
        db.execSQL(createProductTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Se houver atualização do banco de dados, descartamos a tabela antiga e criamos uma nova
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
        onCreate(db)
    }

    fun insertProduct(name: String, description: String, price: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_PRODUCT_NAME, name)
            put(COLUMN_PRODUCT_DESC, description)
            put(COLUMN_PRODUCT_PRICE, price)
        }

        val result = db.insert(TABLE_PRODUCTS, null, contentValues)
        return result != -1L
    }

}
