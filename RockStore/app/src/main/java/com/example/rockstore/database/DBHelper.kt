package com.example.rockstore.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rockstore.R
import com.example.rockstore.model.MusicModel
import com.example.rockstore.model.UserModel

class DBHelper(context: Context) : SQLiteOpenHelper(context, "database.db", null, 1) {
    private val context: Context = context

    val sql = arrayOf(
        "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)",

        "INSERT INTO users (username, password) VALUES ('admin','password')",
        "INSERT INTO users (username, password) VALUES ('rodolfo','password')",
        "INSERT INTO users (username, password) VALUES ('otavio','password')",

        "CREATE TABLE music(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, album TEXT, genre TEXT, year INT, sampleName TEXT, sampleSong INT, infoAlbum TEXT, imageId INT);",

        "INSERT INTO music (name, album, genre, year, sampleName, sampleSong, infoAlbum, imageId) VALUES ('The Sound', 'Jeopardy', 'Post-Punk', 1980, 'The sound - Jeopardy', ${R.raw.thesoundj}, 'Jeopardy is the debut album by British post-punk band The Sound, released in 1980. Characterized by Adrian Borland''s emotive vocals, atmospheric guitars, and introspective lyrics, the album explores themes of alienation and introspection, becoming influential in the post-punk landscape for its distinctive sound and emotional depth.\n\nTracks:\nI Can''t Escape Myself\nHeartland\nHour Of Need\nWords Fail Me\nMissiles\nHeyday\nJeopardy\nNight Versus Day\nResistance\nUnwritten Law\nDesire', ${R.drawable.jeopardy})",
        "INSERT INTO music (name, album, genre, year, sampleName, sampleSong, infoAlbum, imageId) VALUES ('The Sound', 'From the lions mouth', 'Post-Punk', 1981, 'The sound - Contact the fact', ${R.raw.thesoundc}, 'From the Lions Mouth is The Sound''s second album, released in 1981. With Adrian Borland''s evocative vocals, the album blends post-punk and new wave elements. Its introspective lyrics and atmospheric sound continue to delve into emotional depth, making it another influential work in the band''s discography and the post-punk genre.\n\nTracks:\nLions Mouth\nWinning\nSense of Purpose\nContact the Fact\nSkeletons\nJudgment\nFatal Flaw\nPossession\nThe Fire\nSilent Air\nNew Dark Age', ${R.drawable.from_the_lions_mouth})",
        "INSERT INTO music (name, album, genre, year, sampleName, sampleSong, infoAlbum, imageId) VALUES ('Patti Smith', 'Horses', 'Punk Rock', 1975, 'Patti Smith - Birdland', ${R.raw.birdland}, 'Horses is the debut album by Patti Smith, released in 1975. With its poetic and raw lyrics, Smith delivers a fusion of rock and punk elements. The album is known for its influential impact on the punk rock genre and its unique artistic expression.\n\nTracks:\nGloria\nRedondo Beach\nBirdland\nFree Money\nKimberly\nBreak It Up\nLand\nElegie', ${R.drawable.patti_smith_horses})",
        "INSERT INTO music (name, album, genre, year, sampleName, sampleSong, infoAlbum, imageId) VALUES ('Joy Division', 'Unknown Pleasures', 'Post-Punk', 1979, 'Joy Division - Wilderness', ${R.raw.joydivision}, 'Unknown Pleasures is the debut album by Joy Division, released in 1979. The album is known for its atmospheric sound, melancholic lyrics, and distinct post-punk style. It remains a seminal work in the post-punk and alternative music landscape.\n\nTracks:\nDisorder\nDay of the Lords\nCandidate\nInsight\nNew Dawn Fades\nShe''s Lost Control\nShadowplay\nWilderness\nInterzone\nI Remember Nothing', ${R.drawable.joy_division_unknown_pleasure})",
        "INSERT INTO music (name, album, genre, year, sampleName, sampleSong, infoAlbum, imageId) VALUES ('Nick Cave & TBS', 'The Boatmans Call', 'Alternative Rock', 1997, 'Nick Cave & TBS - Into My Arms', ${R.raw.nickcave}, 'The Boatman''s Call is a reflective album by Nick Cave, released in 1997. Cave''s deep and introspective songwriting is showcased in this collection, offering a more stripped-down and intimate musical experience.\n\nTracks:\nInto My Arms\nLime Tree Arbour\nPeople Ain''t No Good\nBrompton Oratory\nThere Is a Kingdom\n(Are You) The One That I''ve Been Waiting For?\nWhere Do We Go Now but Nowhere?\nWest Country Girl\nBlack Hair\nIdiot Prayer\nFar from Me\nGreen Eyes', ${R.drawable.nick_cave_and_the_bad_seeds_boatman})",
        "INSERT INTO music (name, album, genre, year, sampleName, sampleSong, infoAlbum, imageId) VALUES ('Squid', 'Bright Green Field', 'Post-Punk', 2021, 'Squid - GSK', ${R.raw.squid}, 'Bright Green Field is an experimental rock album by Squid, released in 2021. The album showcases Squid''s innovative sound, combining elements of post-punk, art rock, and more. Its dynamic and genre-blurring approach has garnered critical acclaim.\n\nTracks:\nResolution Square\nG.S.K.\nNarrator\nBoy Racers\nPaddling\nDocumentary Filmmaker\n2010\nThe Flyover\nPeel St.\nGlobal Groove\nPamphlets\nRear Admiral', ${R.drawable.bright_green_field})",


        )



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
    fun insertUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.insert("users", null, contentValues)
        db.close()
        return res
    }

    fun updateUser(id: Int, username: String, password: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val res = db.update("users", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deleteUser(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("users", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getUser(username: String, password: String): UserModel {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        var userModel = UserModel()

        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val usernameIndex = c.getColumnIndex("username")
            val passwordIndex = c.getColumnIndex("password")

            userModel = UserModel(
                id = c.getInt(idIndex),
                username = c.getString(usernameIndex),
                password = c.getString(passwordIndex)
            )
        }
        db.close()
        return userModel
    }

    fun login(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM users WHERE username=? AND password=?",
            arrayOf(username, password)
        )
        val userModel = UserModel()

        if (c.count == 1) {
            return true
        } else {
            db.close()
            return false
        }
    }

    /*----------------------------------------------------------------------------------------------
                            CRUD MUSIC
    ----------------------------------------------------------------------------------------------*/
    fun insertMusic(
        name: String,
        album: String,
        genre: String,
        year: String,
        sampleName: String,
        sampleSong: Int,
        infoAlbum: String,
        imageId: Int
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("album", album)
        contentValues.put("genre", genre)
        contentValues.put("year", year)
        contentValues.put("sampleName", sampleName)
        contentValues.put("sampleSong", sampleSong)
        contentValues.put("infoAlbum", infoAlbum)
        contentValues.put("imageId", imageId)
        val res = db.insert("music", null, contentValues)
        db.close()
        return res
    }

    //"CREATE TABLE music(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, album TEXT, genre TEXT, year INT, imageId INT, );"

    fun updatemusic(
        id: Int,
        name: String,
        album: String,
        genre: String,
        year: Int,
        sampleName: String,
        sampleSong: Int,
        infoAlbum: String,
        imageId: Int
    ): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("album", album)
        contentValues.put("genre", genre)
        contentValues.put("year", year)
        contentValues.put("sampleName", sampleName)
        contentValues.put("sampleSong", sampleSong)
        contentValues.put("infoAlbum", infoAlbum)
        contentValues.put("imageId", imageId)
        val res = db.update("music", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun deletemusic(id: Int): Int {
        val db = this.writableDatabase
        val res = db.delete("music", "id=?", arrayOf(id.toString()))
        db.close()
        return res
    }

    fun getMusic(id: Int): MusicModel {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM music WHERE id=?",
            arrayOf(id.toString())
        )
        var musicModel = MusicModel()

        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val albumIndex = c.getColumnIndex("album")
            val genreIndex = c.getColumnIndex("genre")
            val yearIndex = c.getColumnIndex("year")
            val sampleNameIndex = c.getColumnIndex("sampleName")
            val sampleSongIndex = c.getColumnIndex("sampleSong")
            val infoAlbumIndex = c.getColumnIndex("infoAlbum")
            val imageIdIndex = c.getColumnIndex("imageId")

            musicModel = MusicModel(
                id = c.getInt(idIndex),
                name = c.getString(nameIndex),
                album = c.getString(albumIndex),
                genre = c.getString(genreIndex),
                year = c.getInt(yearIndex),
                sampleName = c.getString(sampleNameIndex),
                sampleSong = c.getInt(sampleSongIndex),
                infoAlbum = c.getString(infoAlbumIndex),
                imageId = c.getInt(imageIdIndex),
            )
        }
        db.close()
        return musicModel
    }

    fun getAllMusic(id: Int): MusicModel {
        val db = this.readableDatabase
        val c = db.rawQuery(
            "SELECT * FROM music WHERE id=?",
            arrayOf(id.toString())
        )

        var musicModel = MusicModel()

        if (c.count == 1) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val albumIndex = c.getColumnIndex("album")
            val genreIndex = c.getColumnIndex("genre")
            val yearIndex = c.getColumnIndex("year")
            val sampleNameIndex = c.getColumnIndex("sampleName")
            val sampleSongIndex = c.getColumnIndex("sampleSong")
            val infoAlbumIndex = c.getColumnIndex("infoAlbum")
            val imageIdIndex = c.getColumnIndex("imageId")

            musicModel = MusicModel(
                id = c.getInt(idIndex),
                name = c.getString(nameIndex),
                album = c.getString(albumIndex),
                genre = c.getString(genreIndex),
                year = c.getInt(yearIndex),
                sampleName = c.getString(sampleNameIndex),
                sampleSong = c.getInt(sampleSongIndex),
                infoAlbum = c.getString(infoAlbumIndex),
                imageId = c.getInt(imageIdIndex),
            )
        }
        db.close()
        return musicModel
    }

    fun getAllMusic(): ArrayList<MusicModel> {
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM music", null)
        var listMusicModel = ArrayList<MusicModel>()

        if (c.count > 0) {
            c.moveToFirst()
            val idIndex = c.getColumnIndex("id")
            val nameIndex = c.getColumnIndex("name")
            val albumIndex = c.getColumnIndex("album")
            val genreIndex = c.getColumnIndex("genre")
            val yearIndex = c.getColumnIndex("year")
            val sampleNameIndex = c.getColumnIndex("sampleName")
            val sampleSongIndex = c.getColumnIndex("sampleSong")
            val infoAlbumIndex = c.getColumnIndex("infoAlbum")
            val imageIdIndex = c.getColumnIndex("imageId")
            do {
                val musicModel = MusicModel(
                    id = c.getInt(idIndex),
                    name = c.getString(nameIndex),
                    album = c.getString(albumIndex),
                    genre = c.getString(genreIndex),
                    year = c.getInt(yearIndex),
                    sampleName = c.getString(sampleNameIndex),
                    sampleSong = c.getInt(sampleSongIndex),
                    infoAlbum = c.getString(infoAlbumIndex),
                    imageId = c.getInt(imageIdIndex)
                )
                listMusicModel.add(musicModel)
            } while (c.moveToNext())

        }
        db.close()
        return listMusicModel
    }

}