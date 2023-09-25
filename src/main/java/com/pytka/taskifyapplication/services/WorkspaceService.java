package com.pytka.taskifyapplication.services;

import com.pytka.taskifyapplication.models.WorkspaceDTO;
import com.pytka.taskifyapplication.models.WorkspaceLiteDTO;

import java.util.List;

public interface WorkspaceService {

    List<WorkspaceDTO> getWorkspacesByUserID();

    List<WorkspaceLiteDTO> getWorkspacesLiteByUserID();

    // TODO: finish service when needed

    void addWorkspace(WorkspaceLiteDTO workspaceDTO);
}
