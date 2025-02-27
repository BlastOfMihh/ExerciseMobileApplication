package org.example.cross_unstabilizer.ViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.example.cross_unstabilizer.Model.Exercise
import org.example.cross_unstabilizer.Model.ExerciseDao
import org.example.cross_unstabilizer.Networking.BackendClient

class ExerciseViewModel(private var exerciseDao: ExerciseDao) : ExerciseViewModelLocal(exerciseDao) {

    private val backend = BackendClient()
    var online:Boolean=false
    init {
        if(true){
            syncWithBackend()
            runBlocking {
                backend.connectSocket(
                    mapOf(
                        "refresh" to {args->
                             syncWithBackend()
                        }
                    )
                )
            }
            CoroutineScope(Dispatchers.IO).launch {
                handle_online()
            }
        }
    }

    suspend fun handle_online() {
        while (true) {
            try{
                runBlocking { backend.ping() }
                online = true
            }catch (e:Exception){
                online = false
                println("OFFLINE")
            }
            delay(1000)
        }
    }

    fun syncWithBackend(){
        runBlocking {
            exerciseDao.deleteAll()
            try{
                backend.getAllExercises().forEach {
                    super.createExercise(it.name, it.difficulty, it.description, it.progress, it.id)
                }
            }catch (e:Exception){
                println("Error: $e")
            }
        }
    }

    override fun createExercise(
        name: String,
        difficulty: Int,
        description:String,
        progress: Int,
        id: Int
    ){
        if (!online)
            throw Exception("Add is not supported offline")
        runBlocking {
            try {
                backend.create(name, difficulty, description, progress)
            }catch (e:Exception){
                super.createExercise(name, difficulty, description, progress, -1)
                throw e
            }
        }
        return super.createExercise(name, difficulty, description, progress, -1)
    }

    override fun getById(exerciseId:Int): Exercise?{
        try {
            runBlocking {
                return@runBlocking backend.getById(exerciseId)
            }
        }catch (e:Exception){
            print ("Error: $e")
        }
        return super.getById(exerciseId)
    }

    override fun remove(id: Int){
        try {
            runBlocking {
                backend.remove(id)
            }
        }catch (e:Exception){
            print ("Error: $e")
        }
        return super.remove(id)
    }

    override fun update(
        id: Int,
        name: String,
        difficulty: Int,
        description: String,
        progress: Int
    ){
        runBlocking {
            try {
                backend.update(id, name, difficulty, description, progress)
            }catch (e:Exception){
                print ("Error: $e")
            }
        }
        super.update(id, name, difficulty, description, progress)
    }
}
