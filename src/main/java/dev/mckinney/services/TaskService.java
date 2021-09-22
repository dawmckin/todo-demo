package dev.mckinney.services;

import dev.mckinney.data.TaskData;
import dev.mckinney.models.Task;

import java.util.ArrayList;

public class TaskService {

    private TaskData taskData = new TaskData();

    public void addNewTask(String name) {
        taskData.addPendingTask(new Task(name));
    }

    public void addNewTask(Task newTask) {
        taskData.addPendingTask(newTask);
    }

    public void completeTask(Task task) {
        taskData.completeTaskById(task.getId());
    }

    public ArrayList<Task> getPending() {
        return taskData.getPendingTasks();
    }

    public ArrayList<Task> getAllTasks() {
        return taskData.getAllTasks();
    }

}
