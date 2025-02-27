package org.example.cross_unstabilizer.Validators

class ExerciseValidator{
    companion object{
        private fun validateName(name:String, errors: MutableList<String>){
            if (name.length<3)
                errors.add("Name should have a length greater than 3!!")
        }
        fun validateDifficulty(difficultyString: String, errors: MutableList<String>){
            var difficulty:Int
            try{
                difficulty=difficultyString.toInt()
            }catch (e: Exception){
                errors.add("Difficulty should be an integer!!")
                return
            }
            if(difficulty<0)
                errors.add("Difficulty should be >0!!")
            if(difficulty>10)
                errors.add("Difficulty should be <10!!")
        }
        fun validateProgress(progressString: String, errors: MutableList<String>){
            var progress:Int
            try{
                progress=progressString.toInt()
            }catch (e: Exception){
                errors.add("Progress should be an integer!!")
                return
            }
            if(progress<=0)
                errors.add("Progress should be >=0!!")
            if(progress>10)
                errors.add("Progress should be <10!!")
        }
        fun validate(name:String, difficulty:String, description:String, progress:String){
            var errors = mutableListOf<String>()
            validateName(name,errors)
            validateDifficulty(difficulty,errors)
            validateProgress(progress, errors)
            if(errors.isNotEmpty()) {
                var errorMessage= errors.reduce { accumulator, error ->
                    accumulator+error+"\n"
                }
                throw Exception(errorMessage)
            }
        }
    }
}