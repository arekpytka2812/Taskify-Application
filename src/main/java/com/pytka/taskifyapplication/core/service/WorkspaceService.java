package com.pytka.taskifyapplication.core.service;

import com.pytka.taskifyapplication.core.models.WorkspaceDTO;
import com.pytka.taskifyapplication.core.models.WorkspaceLiteDTO;

import java.util.List;

public interface WorkspaceService {

    List<WorkspaceDTO> getWorkspacesByUserID();

    List<WorkspaceLiteDTO> getWorkspacesLiteByUserID();

    // TODO: finish service when needed

    void addWorkspace(WorkspaceLiteDTO workspaceDTO);

    void deleteWorkspace(Long workspaceID);
}
