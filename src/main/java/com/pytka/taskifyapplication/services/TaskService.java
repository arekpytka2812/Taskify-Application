package com.pytka.taskifyapplication.services;

import com.pytka.taskifyapplication.models.TaskDTO;
import com.pytka.taskifyapplication.models.UpdateInfoDTO;
import com.pytka.taskifyapplication.models.WorkspaceDTO;

import java.util.List;

public interface TaskService {

 //   void getTasks(TaskServiceImpl.TaskCallback callback);

    List<WorkspaceDTO> getTasks();

    List<TaskDTO> getTasksByWorkspaceID(Long workspaceID);

    TaskDTO getTaskByID(Long taskID);

    boolean addTask(TaskDTO taskDTO);
    boolean updateTask(TaskDTO taskDTO);

    boolean addUpdateInfo(Long taskID, UpdateInfoDTO updateInfoDTO);

    boolean deleteTask(Long taskID);
}
