# ExerciseMobileApplication

## Features

- **Local Database (LocalDB)**: Ensures data persistence on the device, even after retrieval, regardless of online, offline, or restart conditions.
- **WebSockets**: Provides real-time notifications for new exercises added to the system, sending messages to all connected clients.
- **Progress Indicator**: Displays a progress indicator during all server or database operations.
- **Error Handling & Logging**: Shows error messages for server or database interactions and logs messages for all interactions.
- **Offline Mode Support**: Allows registering exercises and viewing all exercises in offline mode with appropriate messaging and retry options.
- **User Settings Persistence**: Saves the user's name in application settings to survive app restarts.
- **Dynamic Data Retrieval**: Fetches exercise details from the server each time an exercise is selected.
- **Borrowing Activity Tracking**: Views all exercises borrowed by the user with details such as title, status, and return date.
- **Reports Generation**: Generates reports for authors, exercises by author, top borrowed exercises, and top publishers.
- **Exercise Registration**: Registers new exercises using a POST call.
- **Exercise Removal**: Removes exercises from the system using a DELETE call.
- **Kotlin Multiplatform**: Utilizes Kotlin Multiplatform for shared code across different platforms, enhancing code reusability and maintainability.

## Technologies Used

- **Kotlin Multiplatform**: For sharing code across Android and iOS.
- **Room Database**: For local data storage.
- **WebSockets**: For real-time updates.
- **Retrofit**: For HTTP requests.
- **Jetpack Compose**: For building the UI.
- **Coroutines**: For asynchronous programming.
- **Koin**: For dependency injection.
- **JUnit**: For unit testing.
- **MockK**: For mocking in tests.

## Additional Information

- **BackendClient**: Handles HTTP requests to the backend server for CRUD operations on exercises. See [`BackendClient`](mobileFrontend/composeApp/src/commonMain/kotlin/org/example/cross_unstabilizer/Networking/BackendClient.kt).
- **SocketClient**: Manages WebSocket connections for real-time updates. See [`SocketClient`](mobileFrontend/composeApp/src/commonMain/kotlin/org/example/cross_unstabilizer/Networking/SocketIoClient.kt).
- **ExerciseValidator**: Validates exercise data before performing operations. See [`ExerciseValidator`](mobileFrontend/composeApp/src/commonMain/kotlin/org/example/cross_unstabilizer/Validators/ExerciseValidator.kt).
- **Database Setup**: Configures Room database for local data storage. See [`Database`](mobileFrontend/composeApp/src/commonMain/kotlin/Database.kt) and [`DatabaseAndroid`](mobileFrontend/composeApp/src/androidMain/kotlin/DatabaseAndroid.kt).

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…