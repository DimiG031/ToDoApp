# To-Do App

## Overview
This is a Java Swing-based To-Do List application that allows users to manage their tasks using a tabbed interface. Each tab represents a separate task list, and users can create, rename, and delete tabs as needed. The application also includes a progress bar that updates dynamically based on task completion.

## Features
- **Tabbed Interface**: Users can create up to 10 tabs for organizing different task lists.
- **Task Management**: Add, remove, and mark tasks as done.
- **Task Filtering**: Filter tasks by "Done", "Undone", or "All".
- **Progress Tracking**: A progress bar updates dynamically based on completed tasks.
- **Scrollable Task List**: Added `JScrollPane` for better visibility when task lists get long.
- **Intuitive UI**: Minimalistic design with buttons for task and tab management.

## Installation & Usage
### Prerequisites
- Java Development Kit (JDK) 8 or later
- Any Java IDE (Eclipse, IntelliJ, NetBeans) or command line (terminal)

### Running the Application
1. **Clone the repository**:
   ```sh
   git clone https://github.com/DimiG031/ToDoApp.git
   cd ToDoApp
   ```
2. **Compile and Run**:
   - Using terminal:
     ```sh
     javac ToDoApp.java
     java ToDoApp
     ```
   - Using an IDE:
     - Open the project in an IDE (e.g., IntelliJ IDEA or Eclipse).
     - Run the `ToDoApp` class.

## How It Works
1. Click **"New Tab"** to create a new tab.
2. Enter a task in the text field and click **"Add Task"** to add it.
3. Click on a task's checkbox to mark it as complete.
4. Use the dropdown menu to filter tasks:
   - "All" (default)
   - "Done" (completed tasks only)
   - "Undone" (pending tasks only)
5. The **progress bar** updates dynamically as tasks are checked off.
6. Click on a tab title to rename it (double-click to enter a new name).
7. Click the **"X"** on a tab to close it.

## Future Improvements
- **Persistent storage**: Save and load tasks using a file or database.
- **Drag and drop tasks between tabs**.
- **Dark mode UI**.

## License
This project is open-source under the [MIT License](LICENSE).

---
📩 Feel free to contribute, suggest features, or report issues!

