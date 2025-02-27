package org.example.cross_unstabilizer
import AppDatabase
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.room.RoomDatabase
import getRoomDatabase
import org.example.cross_unstabilizer.Validators.ExerciseValidator
import org.example.cross_unstabilizer.Model.Exercise
import org.example.cross_unstabilizer.Model.ExerciseDao
import org.example.cross_unstabilizer.ViewModel.ExerciseViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(dbBuilder: RoomDatabase.Builder<AppDatabase>) {
//fun App() {
    MaterialTheme {
        val db by remember {mutableStateOf(getRoomDatabase(dbBuilder))}
        val exerciseDao by remember{ mutableStateOf( db.getExerciseDao())}
        val oldStuff by remember{ mutableStateOf( MainActivity2(exerciseDao)) }
        oldStuff.onCreate()
    }
}

class MainActivity2 {

    var viewModelExercise:ExerciseViewModel
    val exerciseDao:ExerciseDao

    constructor(exerciseDao:ExerciseDao){
        this.exerciseDao=exerciseDao
        this.viewModelExercise= ExerciseViewModel(exerciseDao)
    }
    @Composable
    fun onCreate() {
        val navController=rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MainScreenRef
        ){
            composable<MainScreenRef>{
                MainScreen(navController)
            }
            composable<AddScreenRef>{
                AddScreen(navController)
            }
            composable<UpdateScreenRef>{
                var exerciseId = it.toRoute<UpdateScreenRef>().id
                //var exercise = Exercise(UUID.randomUUID(),"", 1,"",1)
                UpdateScreen(navController, exerciseId)
            }
            composable<DetailsScreenRef>{
                var exerciseId = it.toRoute<UpdateScreenRef>().id
                DetailScreen(navController, exerciseId)
            }
        }
    }
    @Serializable
    object MainScreenRef
    @Serializable
    object AddScreenRef
    @Serializable
    data class UpdateScreenRef(val id: Int)
    @Serializable
    data class DetailsScreenRef(val id: Int)

    @Composable
    fun UpdateScreen(navController: NavController, exerciseId: Int){
        print(exerciseId)
        var showDialog by remember {mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("unkown") }
        var exercise = viewModelExercise.getById(exerciseId)?: Exercise(0, "0", 0, "0", 1)
        var name by remember {mutableStateOf(exercise.name) }
        var difficulty by remember { mutableStateOf(exercise.difficulty.toString()) }
        var description by remember{mutableStateOf(exercise.description)}
        var progress by remember { mutableStateOf(exercise.progress.toString()) }
        Column (modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                navController.navigate(MainScreenRef)
            }){
                Text("Back to main screen")
            }
            EditableExerciseProperties(
                name, {value:String -> name=value },
                difficulty, {value:String -> difficulty=value},
                description, {value:String -> description=value},
                progress, {value:String -> progress=value}
            )
            Button(onClick = {
                try {
                    ExerciseValidator.validate(name, difficulty, description, progress)
                    viewModelExercise.update(exercise.id,name,difficulty.toInt(),description,progress.toInt())
                    navController.navigate(MainScreenRef)
                }catch (e: Exception){
                    showDialog=true
                    errorMessage = e.message ?: "An unknown error occurred."
                }
            }) {
                Text("Update $name")
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Error") },
                    text = { Text(errorMessage) },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("OK")
                        }
                    },
                )
            }
        }
    }
    @Composable
    fun EditableExerciseProperties(name:String, onNameChanged:(String)-> Unit, difficulty:String, onDifficultyChanged:(String)-> Unit, description: String, onDescriptionChanged:(String)->Unit, progress:String, onProgressChanged:(String)-> Unit, isEditable: Boolean=true){
        Column {
            Text("Name:")
            if(isEditable)
                TextField(
                    value = name,
                    onValueChange = onNameChanged
                )
            else{
                Text(name)
            }
            Text("Difficulty:")
            if(isEditable)
                TextField(
                    value = difficulty,
                    onValueChange = {value->onDifficultyChanged.invoke(value)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            else
                Text(difficulty)

            Text("Description:")
            if(isEditable)
                TextField(
                    value = description,
                    onValueChange = onDescriptionChanged
                )
            else
                Text(description)

            Text("Progress:")
            if(isEditable)
                TextField(
                    value = progress,
                    onValueChange = onProgressChanged,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            else
                Text(progress)
        }
    }
    @Composable
    fun AddScreen(navController: NavController){
        var showDialog by remember {mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf("unkown") }
        var name by remember {mutableStateOf("") }
        var difficulty by remember { mutableStateOf("1") }
        var description by remember{mutableStateOf("")}
        var progress by remember { mutableStateOf("1") }
        Column (modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                //navController.navigate(MainScreenRef)
                navController.popBackStack()
            }){
                Text("Back to main screen")
            }
            EditableExerciseProperties(
                name, {value:String -> name=value },
                difficulty, {value:String -> difficulty=value},
                description, {value:String -> description=value},
                progress, {value:String -> progress=value}
            )
            Button(onClick = {
                try {
                    ExerciseValidator.validate(name, difficulty, description, progress)
                    viewModelExercise.createExercise(name,difficulty.toInt(),description,progress.toInt())
                    //navController.navigate(MainScreenRef)
                    navController.popBackStack()
                }catch (e: Exception){
                    showDialog=true
                    errorMessage = e.message ?: "An unknown error occurred."
                }
            }) {
                Text("Add new exercise")
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Error") },
                    text = { Text(errorMessage) },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("OK")
                        }
                    },
                )
            }
        }
    }

    @Composable
    fun MainScreen(navController: NavController){
        Column( ) {
            Text( text="Fitness broski" )
            Button(onClick = {
                navController.navigate(AddScreenRef)
            }) {
                Text("Add new Exercise")
            }
            ListOfExercises(navController)
        }
    }

    @Composable
    fun ListOfExercises(navController: NavController){
        val exercises by viewModelExercise.listOfExercises.collectAsState(initial = emptyList())
        LazyColumn (
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ){
            items(exercises){ exercise->
                ExerciseDisplay(navController=navController, exercise)
            }
        }
    }

    @Composable
    fun ExerciseDisplay(navController: NavController, exercise: Exercise){
        Column (){
            Text(text=exercise.name)
            Row {
                Button(onClick = {
                    navController.navigate(DetailsScreenRef(exercise.id))
                }) {
                    Text("Details")
                }
                Button(onClick = {
                    navController.navigate(UpdateScreenRef(exercise.id))
                }) {
                    Text("Update")
                }
                Button(onClick = {
                    viewModelExercise.remove(exercise.id)
                }) {
                    Text("Remove")
                }
            }
        }
    }

    @Composable
    fun DetailScreen(navController: NavController, exerciseId: Int){
        var exercise=viewModelExercise.getById(exerciseId)
        if(exercise==null)
            return
        var name by remember {mutableStateOf(exercise.name) }
        var difficulty by remember { mutableStateOf(exercise.difficulty.toString()) }
        var description by remember{mutableStateOf(exercise.description.toString())}
        var progress by remember { mutableStateOf(exercise.progress.toString()) }
        Column (modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                navController.navigate(MainScreenRef)
            }){
                Text("Back to main screen")
            }
            EditableExerciseProperties(
                name, {value:String -> name=value },
                difficulty, {value:String -> difficulty=value},
                description, {value:String -> description=value},
                progress, {value:String -> progress=value},
                isEditable = false
            )
            Button(onClick = {
                navController.navigate(MainScreenRef)
            }) {
                Text("Go back")
            }
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    UnstabilizerTheme {
//        MainScreen()
//    }
//}