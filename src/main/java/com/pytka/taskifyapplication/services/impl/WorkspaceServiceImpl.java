package com.pytka.taskifyapplication.services.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.models.WorkspaceDTO;
import com.pytka.taskifyapplication.models.WorkspaceLiteDTO;
import com.pytka.taskifyapplication.services.RequestService;
import com.pytka.taskifyapplication.services.WorkspaceService;
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

}
