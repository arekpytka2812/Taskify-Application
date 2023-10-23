package com.pytka.taskifyapplication.core.service.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.models.WorkspaceDTO;
import com.pytka.taskifyapplication.core.models.WorkspaceLiteDTO;
import com.pytka.taskifyapplication.core.service.RequestService;
import com.pytka.taskifyapplication.core.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {


    private final RequestService requestService;

    @Override
    public List<WorkspaceDTO> getWorkspacesByUserID(){

        String endpointURL = "/workspace/" + SpringMainApplication.USER_ID;

        return this.requestService.getListRequest(WorkspaceDTO.class, endpointURL);
    }

    @Override
    public List<WorkspaceLiteDTO> getWorkspacesLiteByUserID(){

        String endpointURL = "/workspace/lite/" + SpringMainApplication.USER_ID;

        return this.requestService.getListRequest(WorkspaceLiteDTO.class, endpointURL);
    }

    @Override
    public void addWorkspace(WorkspaceLiteDTO workspaceDTO){

        String endpointURL = "/workspace/add/" + SpringMainApplication.USER_ID;

        this.requestService.postRequest(Boolean.class, workspaceDTO, endpointURL);
    }

    @Override
    public void deleteWorkspace(Long workspaceID){

        String endpointURL = "/workspace/" + workspaceID;

        this.requestService.deleteRequest(Void.class, endpointURL);
    }

}
