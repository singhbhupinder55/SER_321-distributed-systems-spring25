package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;

public class ToDoListServiceImpl extends ToDoListGrpc.ToDoListImplBase {
    // Thread-safe storage for tasks
    private static final String FILE_PATH = "tasks.json";
    private static final Gson gson = new Gson();
    private final Map<String, List<Task>> userTasks = new ConcurrentHashMap<>();

    public ToDoListServiceImpl() {
        loadTasksFromFile();  // Load tasks when server starts
    }

    private void saveTasksToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(userTasks, writer);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<String, List<Task>>>() {}.getType();
            Map<String, List<Task>> loadedTasks = gson.fromJson(reader, type);
            if (loadedTasks != null) {
                userTasks.putAll(loadedTasks);
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }
    }

    @Override
    public void addTask(TaskRequest request, StreamObserver<TaskResponse> responseObserver) {
        String username = request.getUser().toLowerCase();
        String taskDescription = request.getTaskDescription();

        Task newTask = Task.newBuilder()
                .setId(userTasks.getOrDefault(username, new ArrayList<>()).size() + 1) // Start from 1
                .setDescription(taskDescription)
                .setIsCompleted(false)
                .build();

        userTasks.putIfAbsent(username, new ArrayList<>());
        userTasks.get(username).add(newTask);
        saveTasksToFile(); // Persist task data

        TaskResponse response = TaskResponse.newBuilder()
                .setIsSuccess(true)
                .setMessage("Task added successfully for " + username)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void completeTask(TaskUpdateRequest request, StreamObserver<TaskResponse> responseObserver) {
        String username = request.getUser().toLowerCase();
        int taskId = request.getTaskId() - 1;  // Convert to 0-based index

        if (!userTasks.containsKey(username) || taskId < 0 || taskId >= userTasks.get(username).size()) {
            responseObserver.onNext(TaskResponse.newBuilder()
                    .setIsSuccess(false)
                    .setMessage("Invalid task number or user does not exist")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        Task task = userTasks.get(username).get(taskId);
        if (task.getIsCompleted()) {
            responseObserver.onNext(TaskResponse.newBuilder()
                    .setIsSuccess(false)
                    .setMessage("Task is already marked as completed.")
                    .build());
            responseObserver.onCompleted();
            return;
        }

        // Mark task as completed
        Task updatedTask = Task.newBuilder()
                .setId(task.getId())
                .setDescription(task.getDescription())
                .setIsCompleted(true)
                .build();

        userTasks.get(username).set(taskId, updatedTask);
        saveTasksToFile(); // Persist task data

        responseObserver.onNext(TaskResponse.newBuilder()
                .setIsSuccess(true)
                .setMessage("Task marked as completed.")
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void getTasks(UserRequest request, StreamObserver<TaskListResponse> responseObserver) {
        String username = request.getUser().toLowerCase();
        List<Task> tasks = userTasks.getOrDefault(username, new ArrayList<>());

        TaskListResponse response = TaskListResponse.newBuilder()
                .setIsSuccess(true)
                .addAllTasks(tasks)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

