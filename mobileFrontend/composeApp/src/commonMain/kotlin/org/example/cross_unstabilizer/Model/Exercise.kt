package org.example.cross_unstabilizer.Model


import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable


@Serializable
@Entity
data class Exercise (
    @PrimaryKey(autoGenerate = true) var id: Int=0,
    @ColumnInfo var name: String,
    @ColumnInfo var difficulty: Int,
    @ColumnInfo var description:String,
    @ColumnInfo var progress: Int
)

@Dao
interface ExerciseDao{
    @Query("SELECT * FROM exercise")
    fun getAll() : Flow<List<Exercise>>

    @Upsert
    suspend fun upsert(exercise:Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Query("SELECT * FROM exercise where id=:id")
    suspend fun getById(id:Int):Exercise

    @Query("DELETE FROM exercise")
    suspend fun deleteAll()
}