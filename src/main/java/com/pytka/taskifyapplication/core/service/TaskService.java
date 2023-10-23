package com.pytka.taskifyapplication.core.service;

import com.pytka.taskifyapplication.core.models.TaskDTO;
import com.pytka.taskifyapplication.core.models.UpdateInfoDTO;
import com.pytka.taskifyapplication.core.models.WorkspaceDTO;

import java.util.List;

public interface TaskService {

 //   void getTasks(TaskServiceImpl.TaskCallback callback);

    List<WorkspaceDTO> getTasks();

    List<TaskDTO> getTasksByWorkspaceID(Long workspaceID);

    TaskDTO getTaskByID(Long taskID);

    void addTask(TaskDTO taskDTO);
    void updateTask(TaskDTO taskDTO);

    void addUpdateInfo(Long taskID, UpdateInfoDTO updateInfoDTO);

    void deleteTask(Long taskID);
}
