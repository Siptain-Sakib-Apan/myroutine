# MyRoutine

#### Video Demo: <INSERT VIDEO URL HERE>

#### Description:
MyRoutine is a simple and intuitive Android application designed to help users manage their daily tasks and routines efficiently. The primary goal of this app is to provide a lightweight solution for organizing tasks, ensuring that users can easily add, edit, delete, and track their daily activities. By combining an easy-to-use interface with persistent storage, MyRoutine allows users to maintain an up-to-date to-do list that survives app closures and device restarts, making task management seamless and reliable.

The app features a clean and visually appealing interface with a top toolbar that clearly displays the app’s name. The toolbar background is white, while the title text uses a subtle color that is easy on the eyes, ensuring readability without causing strain. Each task is displayed in a list format, with a checkbox on the right side allowing users to mark tasks as completed. When a task is marked as complete, its text is immediately crossed out in real-time, providing instant visual feedback. This feature enhances usability by letting users track progress without any delays.

Users can add new tasks by tapping the “Add” button, which opens a dialog box prompting them to enter the task details. Once entered, the task appears immediately in the list with an unchecked checkbox by default. Editing tasks is straightforward: users can tap on a task to view options for editing or deleting it. Deleting tasks removes them from the list and updates the stored data to ensure consistency. Additionally, the “Clear All” button allows users to remove all tasks at once, resetting the app while maintaining its integrity. Throughout these interactions, all task data and checked states are saved persistently using Android’s `SharedPreferences`, ensuring that user data is retained even after closing the app.

#### How to Run:
To run MyRoutine, users need to have Android Studio installed on their device. First, clone or download the project repository from GitHub. Open the project in Android Studio by selecting the project folder from the welcome screen. Once the project is loaded, sync the Gradle files to ensure all dependencies are correctly set up. After syncing, users can build and run the app either on an Android emulator or a physical device connected via USB. The app launches directly into the main activity, displaying the list of tasks. From there, users can interact with the app by adding, editing, or deleting tasks, as well as marking tasks as completed.

#### Key Files:
- **Main.kt** – This is the main activity of the application. It contains all the core logic, including adding, editing, deleting tasks, handling checkbox states, and saving/loading tasks from persistent storage.
- **main.xml** – This layout file defines the main screen of the app, including the ListView that displays tasks, the “Add” button, and the “Clear All” button.
- **item_task.xml** – This layout defines how each task is displayed in the ListView, including the TextView for the task name and the CheckBox for marking tasks as complete.
- **themes.xml & colors.xml** – These files define the app’s visual style, including colors, themes, and text appearances, ensuring a consistent and pleasant user experience.

#### Technical Details:
The app is developed in Kotlin, using Android Studio as the IDE. The user interface relies on XML layouts, while the core logic is implemented in Kotlin within `Main.kt`. Tasks are stored persistently using Android’s `SharedPreferences`. Each task is saved as a string, and the completion status of each task is saved as a Boolean string (`true` or `false`). When the app starts, the tasks and their completion states are loaded from `SharedPreferences`, ensuring that users never lose their data. The ListView adapter is customized to handle real-time updates, such as crossing out completed tasks immediately when a checkbox is toggled. This approach ensures that the app is responsive and user-friendly.

All interactions, including adding, editing, and deleting tasks, are handled with AlertDialogs, providing a clean and intuitive way for users to manage their routines. Long-pressing a task allows for quick deletion, while single taps provide options to edit or delete, giving users flexibility in how they interact with their task list. The app also includes proper handling of edge cases, such as clearing all tasks or adding tasks with empty text, making it robust and reliable for everyday use.

In conclusion, MyRoutine is a fully functional, user-friendly task management app designed to help users organize their daily activities efficiently. Its persistent storage, real-time visual feedback, and simple interface make it a practical tool for anyone looking to improve productivity and keep track of their routines on an Android device.
