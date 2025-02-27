# ExerciseMobileApplication

Local Database (LocalDB): Persist data on the device after retrieval, regardless of online, offline, or restart conditions.
WebSockets: Server notifications for new exercises added to the system, sending messages to all connected clients.
Progress Indicator: Display a progress indicator during all server or database operations.
Error Handling & Logging: Display error messages for server or database interactions and log messages for all interactions.
Offline Mode Support: Ability to register exercises and view all exercises in offline mode with appropriate messaging and retry options.
User Settings Persistence: Persist the user's name in application settings to survive app restarts.
Dynamic Data Retrieval: Fetch exercise details from the server each time an exercise is selected.
Borrowing Activity Tracking: View all exercises borrowed by the user with details such as title, status, and return date.
Reports Generation: Generate reports for authors, exercises by author, top borrowed exercises, and top publishers.
Exercise Registration: Register new exercises using a POST call.
Exercise Removal: Remove exercises from the system using a DELETE call.
Kotlin Multiplatform: Utilize Kotlin Multiplatform for shared code across different platforms, enhancing code reusability and maintainability.
