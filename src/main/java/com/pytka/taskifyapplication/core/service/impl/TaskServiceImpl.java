package com.pytka.taskifyapplication.core.service.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.models.TaskDTO;
import com.pytka.taskifyapplication.core.models.UpdateInfoDTO;
import com.pytka.taskifyapplication.core.models.WorkspaceDTO;
import com.pytka.taskifyapplication.core.service.RequestService;
import com.pytka.taskifyapplication.core.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final RequestService requestService;

    @Override
    public List<WorkspaceDTO> getTasks(){

        String endpointURI = "/workspace/" + SpringMainApplication.USER_ID;

        return this.requestService.getListRequest(WorkspaceDTO.class, endpointURI);
    }

    @Override
    public List<TaskDTO> getTasksByWorkspaceID(Long workspaceID){

        String endpointURL = "/tasks/getAll/" + workspaceID;

        return this.requestService.getListRequest(TaskDTO.class, endpointURL);
    }

    @Override
    public TaskDTO getTaskByID(Long taskID){

        String endpointURL = "/tasks/get/" + taskID;

        return this.requestService.getRequest(TaskDTO.class, endpointURL);
    }

    @Override
    public void addTask(TaskDTO taskDTO) {

        String endpointURI = "/tasks/add/" + SpringMainApplication.USER_ID;

        this.requestService.postRequest(Void.class, taskDTO, endpointURI);
    }

    @Override
    public void updateTask(TaskDTO taskDTO) {

        String endpointURI = "/tasks/update/" + taskDTO.getID();

        this.requestService.postRequest(Void.class, taskDTO, endpointURI);
    }

    @Override
    public void addUpdateInfo(Long taskID, UpdateInfoDTO updateInfoDTO) {

        String endpointURI = "/tasks/updateInfo/" + taskID;

        this.requestService.postRequest(Void.class, updateInfoDTO, endpointURI);
    }

    @Override
    public void deleteTask(Long taskID) {

        String endpointURI = "/tasks/delete/" + taskID;

        this.requestService.deleteRequest(Void.class, endpointURI);
    }

    public interface TaskCallback{
        void onTaskFetched(List<WorkspaceDTO> taskDTO);
    }
}
