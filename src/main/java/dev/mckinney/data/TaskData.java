package dev.mckinney.data;

import dev.mckinney.models.Task;
import dev.mckinney.services.ConnectionService;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskData {

    private ConnectionService connectionService = new ConnectionService();

    public ArrayList<Task> getAllTasks() {
        return getSubsetOfTasks("all");
    }

    private ArrayList<Task> getSubsetOfTasks(String criteria) {
        String sql = "";
        switch(criteria) {
            case "all":
                sql = "select * from task order by task_id";
                break;
            case "pending":
                sql = "select * from task where is_completed = false order by task_id";
                break;
            case "completed":
                sql = "select * from task where is_completed = true order by task_id";
                break;
            default:
                throw new RuntimeException("not supported criteria");
        }
        return getTasksBySql(sql);
    }

    private ArrayList<Task> getTasksBySql(String sql) {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Connection c = connectionService.establishConnection();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

            while(rs.next()) {
                int id = rs.getInt("task_id");
                String name = rs.getString("task_name");
                boolean isCompleted = rs.getBoolean("is_completed");
                String description = rs.getString("description");
                Task t = new Task();
                t.setId(id);
                t.setTaskName(name);
                t.setCompleted(isCompleted);
                t.setDescription(description);
                tasks.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public ArrayList<Task> getPendingTasks() {
        return getSubsetOfTasks("pending");
    }

    public void addPendingTask(Task newTask) {
        String sql = "insert into task (task_name, description, is_completed) values ( ? , ?, false )";

        try (Connection c = connectionService.establishConnection();
             PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, newTask.getTaskName());
            pstmt.setString(2, newTask.getDescription());
            pstmt.executeUpdate(); // returns a number indicating the rows that were affected
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateData(Task task) {

    }

    public void completeTaskById(int id) {
        String sql = "update task set is_completed = true where task_id = ?";

        try {
            Connection c = connectionService.establishConnection();
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate(); // returns a number indicating the rows that were affected
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
