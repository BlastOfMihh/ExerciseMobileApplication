import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

// shared/src/androidMain/kotlin/DatabaseAndroid.kt

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}