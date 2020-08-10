package mx.arturo.triple.model.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ActiveRoom::class], version = 1)
abstract class ActiveDatabase : RoomDatabase() {
    abstract val activeDatabaseDao : ActiveDatabaseDao
    companion object{
        @Volatile
        private var INSTANCE : ActiveDatabase ?= null

        fun getInstance(context: Context) : ActiveDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ActiveDatabase::class.java,
                        "active_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}