package com.pytka.taskifyapplication.services;

import com.pytka.taskifyapplication.models.TaskDTO;
import com.pytka.taskifyapplication.models.UpdateInfoDTO;
import com.pytka.taskifyapplication.models.WorkspaceDTO;
import com.pytka.taskifyapplication.services.impl.TaskServiceImpl;
import javafx.concurrent.Task;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface TaskService {

    void getTasks(TaskServiceImpl.TaskCallback callback);

    boolean addTask(TaskDTO taskDTO);
    boolean updateTask(TaskDTO taskDTO);

    boolean addTaskUpdate(Long taskID, UpdateInfoDTO updateInfoDTO);

    boolean deleteTask(Long taskID);
}
