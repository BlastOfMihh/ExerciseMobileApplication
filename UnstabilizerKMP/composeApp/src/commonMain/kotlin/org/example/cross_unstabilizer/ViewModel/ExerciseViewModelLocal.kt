package org.example.cross_unstabilizer.ViewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.example.cross_unstabilizer.Model.Exercise
import org.example.cross_unstabilizer.Model.ExerciseDao

open class ExerciseViewModelLocal(private var exerciseDao: ExerciseDao) : ViewModel() {

    val listOfExercises = exerciseDao.getAll()

    init {
        if (false) {
            createExercise("Battle the gods", 2, "this is it", 2)
            createExercise("Defending Constantinople", 2, "this is it", 2)
            createExercise("read the manual", 2, "this is it", 2)
        }
    }

    open fun createExercise(
        name: String,
        difficulty: Int,
        description:String,
        progress: Int,
        id: Int = -1
    ){
        val exercise = Exercise(
            name=name,
            difficulty = difficulty,
            description = description,
            progress = progress
        )
        if(id!=-1){
            exercise.id=id
        }
        runBlocking {
            exerciseDao.upsert(exercise)
        }
    }

    open fun getById(exerciseId:Int): Exercise?{
        var foundExercise:Exercise
        runBlocking {
            foundExercise=exerciseDao.getById(exerciseId)
        }
        return foundExercise
    }

    open fun remove(id: Int){
        try {
            runBlocking {
                exerciseDao.delete(exerciseDao.getById(id))
            }
        }catch (e:Exception){

        }
    }

    open fun update(
        id: Int,
        name: String,
        difficulty: Int,
        description: String,
        progress: Int
    ){
        runBlocking {
            val exercise=exerciseDao.getById(id)
            exercise.name=name
            exercise.progress=progress
            exercise.description=description
            exercise.difficulty=difficulty
            exerciseDao.upsert(exercise)
        }
    }

}
