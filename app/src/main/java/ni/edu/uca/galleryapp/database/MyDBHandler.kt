package ni.edu.uca.galleryapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ni.edu.uca.galleryapp.models.Image
import java.io.ByteArrayOutputStream

class MyDBHandler(context: Context)
    : SQLiteOpenHelper(context, NAME, null, VERSION) {
    companion object {
        val NAME = "images database"
        val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE images(id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOB NOT NULL)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
       db!!.execSQL("DROP TABLE IF EXISTS  images")
        onCreate(db)
    }

    fun addImage(bitmap: Bitmap):  Boolean{
        val cv = ContentValues()
        val byteArrayOutputStream  = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        cv.put("image", byteArray)
        return writableDatabase.insert("images", null, cv ) > 0
    }

fun getAllImages(): ArrayList<Image>{
        val arr = arrayListOf<Image>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM images", null)
        if (cursor.count  > 0 ){
                cursor.moveToFirst()
                while (!cursor.isAfterLast){
                    val id = cursor.getInt(0)
                    val byteArray = cursor.getBlob(1)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    val image = Image(id, bitmap)
                    arr.add(image)
                    cursor.moveToNext()
                }
            }
        cursor.close()
        return  arr
    }
}


