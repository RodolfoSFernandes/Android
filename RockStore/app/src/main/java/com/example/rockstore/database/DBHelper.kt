package com.example.rockstore.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rockstore.model.MusicModel
import com.example.rockstore.model.UserModel

class DBHelper(context:Context) : SQLiteOpenHelper(context,"database.db",null,1) {

    val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",
        "INSERT INTO users (username, password) VALUES ('admin','password')",
        "CREATE TABLE music(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, album TEXT, genre TEXT, year INT, imageId INT);",
        "INSERT INTO music (name, album, genre, year, imageId) VALUES ('The Sound', 'From the lions mouth', 'Post-Punk', 1981, 1)",
        "INSERT INTO music (name, album, genre, year, imageId) VALUES ('The Sound', 'Jeopardy', 'Post-Punk', 1980, 2)",
    )
    //val id: Int = 0,
    //val name: String = "",
    //val album: String = "",
    //val genre: String = "",
    //val year: INT = 0,
    //val imageId: Int = 0

    override fun onCreate(db: SQLiteDatabase) {
      sql.forEach {
          db.execSQL(it)
      }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    /*----------------------------------------------------------------------------------------------
                            CRUD USERS
    ----------------------------------------------------------------------------------------------*/
    fun insertUser(username:String, password: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.insert("users",null,contentValues)
        return res
    }

    fun updateUser(id: Int, username:String, password: String): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.update("users" ,contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteUser(id: Int): Int{
        val db = this.writableDatabase
        val res = db.delete("users","id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getUser(username: String, password: String): UserModel{
        val db = this.readableDatabase
        val c = db.rawQuery("select * from users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        var userModel = UserModel()

        if(c.count==1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val usernameIndex = c.getColumnIndex("username")
            val passwordIndex = c.getColumnIndex("password")

            userModel = UserModel(
            id = c.getInt(idIndex),
            username = c.getString(usernameIndex),
            password = c.getString(passwordIndex))
        }
        db.close()
        return userModel
    }
    fun login(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "select * from users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        val userModel = UserModel()

        return if (c.count == 1) {
            db.close()
            true
        }else {
            db.close()
            false
        }
    }

    /*----------------------------------------------------------------------------------------------
                            CRUD MUSIC
    ----------------------------------------------------------------------------------------------*/
    fun insertMusic(name: String, album: String, genre: String, year: String, imageId: Int): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("album", album)
        contentValues.put("genre", genre)
        contentValues.put("year", year)
        contentValues.put("imageId", imageId)
        val res = db.insert("music",null,contentValues)
        return res
    }

    //"CREATE TABLE music(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, album TEXT, genre TEXT, year INT, imageId INT, );"

    fun updatemusic(id: Int, name: String, album: String, genre: String, year: Int, imageId: Int): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("album", album)
        contentValues.put("genre", genre)
        contentValues.put("year", year)
        contentValues.put("imageId", imageId)
        val res = db.update("music" ,contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deletemusic(id: Int): Int{
        val db = this.writableDatabase
        val res = db.delete("music","id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getMusic(id: Int): MusicModel {
        val db = this.readableDatabase
        val c = db.rawQuery("select * from music WHERE id=?",
            arrayOf(id.toString())
        )
        var musicModel = MusicModel()

        if(c.count==1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val albumIndex = c.getColumnIndex("album")
            val genreIndex = c.getColumnIndex("genre")
            val yearIndex = c.getColumnIndex("year")
            val imageIdIndex = c.getColumnIndex("imageId")

            musicModel = MusicModel(
                id = c.getInt(idIndex),
                name = c.getString(nameIndex),
                album = c.getString(albumIndex),
                genre = c.getString(genreIndex),
                year = c.getInt(yearIndex),
                imageId = c.getInt(imageIdIndex),
            )


        }
        db.close()
        return musicModel
    }
    fun getAllMusic(): ArrayList<MusicModel>  {
        val db = this.readableDatabase
        val c = db.rawQuery("select * from music",null)
        var listMusicModel = ArrayList<MusicModel>()

        if(c.count>0) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val albumIndex = c.getColumnIndex("album")
            val genreIndex = c.getColumnIndex("genre")
            val yearIndex = c.getColumnIndex("year")
            val imageIdIndex = c.getColumnIndex("imageId")
            do{
                val musicModel = MusicModel(
                    id = c.getInt(idIndex),
                    name = c.getString(nameIndex),
                    album = c.getString(albumIndex),
                    genre = c.getString(genreIndex),
                    year = c.getInt(yearIndex),
                    imageId = c.getInt(imageIdIndex),
                )
                listMusicModel.add(musicModel)
            } while (c.moveToNext())

        }
        db.close()
        return listMusicModel
    }

}