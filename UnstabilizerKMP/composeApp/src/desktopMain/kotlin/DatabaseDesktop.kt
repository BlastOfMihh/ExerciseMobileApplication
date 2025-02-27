import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

//@file:JvmName("SomethingUnique")

fun getMyDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile.absolutePath,
    )
}