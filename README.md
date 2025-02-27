# ExerciseMobileApplication

## Features
* Local Database (LocalDB): Ensures data persistence on the device, even after retrieval, regardless of online, offline, or restart conditions.
* WebSockets: Provides real-time notifications for new exercises added to the system, sending messages to all connected clients.
* Progress Indicator: Displays a progress indicator during all server or database operations.
* Error Handling & Logging: Shows error messages for server or database interactions and logs messages for all interactions.
* Offline Mode Support: Allows registering exercises and viewing all exercises in offline mode with appropriate messaging and retry options.
* User Settings Persistence: Saves the user's name in application settings to survive app restarts.
* Dynamic Data Retrieval: Fetches exercise details from the server each time an exercise is selected.
* Borrowing Activity Tracking: Views all exercises borrowed by the user with details such as title, status, and return date.
* Reports Generation: Generates reports for authors, exercises by author, top borrowed exercises, and top publishers.
* Exercise Registration: Registers new exercises using a POST call.
* Exercise Removal: Removes exercises from the system using a DELETE call.
* Kotlin Multiplatform: Utilizes Kotlin Multiplatform for shared code across different platforms, enhancing code reusability and maintainability.

## Additional Information
* BackendClient: Handles HTTP requests to the backend server for CRUD operations on exercises. See BackendClient.
* SocketClient: Manages WebSocket connections for real-time updates. See SocketClient.
* ExerciseViewModel: Synchronizes local data with the backend and handles offline support. See ExerciseViewModel.
* ExerciseValidator: Validates exercise data before performing operations. See ExerciseValidator.
* Database Setup: Configures Room database for local data storage. See Database and DatabaseAndroid.