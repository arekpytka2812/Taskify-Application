package com.pytka.taskifyapplication.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.models.TaskDTO;
import com.pytka.taskifyapplication.models.UpdateInfoDTO;
import com.pytka.taskifyapplication.models.WorkspaceDTO;
import com.pytka.taskifyapplication.services.RequestService;
import com.pytka.taskifyapplication.services.TaskService;
import javafx.concurrent.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final RequestService requestService;

//    @Override
//    public void getTasks(TaskCallback callback) {
//        String endpointURI = "/workspace/" + SpringMainApplication.USER_ID;
//        CompletableFuture.supplyAsync(() -> this.requestService.getListRequest(WorkspaceDTO.class, endpointURI))
//                .thenAccept(callback::onTaskFetched)
//                .exceptionally(ex ->{
//                    callback.onTaskFetched(null);
//                    return null;
//                });
//
//
//    }

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
    public boolean addTask(TaskDTO taskDTO) {

        String endpointURI = "/tasks/add/" + SpringMainApplication.USER_ID;

        return this.requestService.postRequest(Boolean.class, taskDTO, endpointURI);
    }

    @Override
    public boolean updateTask(TaskDTO taskDTO) {

        String endpointURI = "/tasks/update/" + taskDTO.getID();

        return this.requestService.postRequest(Boolean.class, taskDTO, endpointURI);
    }

    @Override
    public boolean addUpdateInfo(Long taskID, UpdateInfoDTO updateInfoDTO) {

        String endpointURI = "/tasks/updateInfo/" + taskID;

        return this.requestService.postRequest(Boolean.class, updateInfoDTO, endpointURI);
    }

    @Override
    public boolean deleteTask(Long taskID) {

        String endpointURI = "/tasks/delete/" + taskID;

        return this.requestService.deleteRequest(Boolean.class, endpointURI);
    }

    public interface TaskCallback{
        void onTaskFetched(List<WorkspaceDTO> taskDTO);
    }
}
