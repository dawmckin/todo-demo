package dev.mckinney.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mckinney.models.Task;
import dev.mckinney.services.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TaskServlet extends HttpServlet {

    private TaskService taskService = new TaskService();
    private ObjectMapper objectMapper = new ObjectMapper();

    /*
        this method will respond to GET requests sent to /tasks
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // process GET request and prepare response
        System.out.println("GET request to /tasks");

        // obtain all of our task record
        ArrayList<Task> tasks = taskService.getAllTasks();

        // convert task records from java objects to JSO
        String pendingTasksJson = objectMapper.writeValueAsString(tasks);
        System.out.println(pendingTasksJson);

        // send back task data via the response body
        try(PrintWriter pw = response.getWriter()) {
            pw.write(pendingTasksJson);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST request to /tasks");

        // obtain the JSON task from the request body
        try(BufferedReader reader = request.getReader();
            PrintWriter pw = response.getWriter();) {

            //this assumes the data is only on one line
            String taskJson = reader.readLine();
            System.out.println(taskJson);

            //convert JSON to task object
            Task task = objectMapper.readValue(taskJson, Task.class);
            System.out.println(task);

            if(task == null || task.getTaskName() == null || task.getTaskName().isEmpty()) {
                response.setStatus(400);
            } else {
                //use service to add new actor
                taskService.addNewTask(task);
                response.setStatus(201);
            }
        }
    }

}
