package com.pytka.taskifyapplication.core.service;

import com.pytka.taskifyapplication.core.models.TaskPrioritiesDTO;
import com.pytka.taskifyapplication.core.models.TaskTypesDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;

public interface UserSettingsService {

    UserSettingsDTO getUserSettings();

    TaskTypesDTO getTaskTypes();

    TaskPrioritiesDTO getTaskPriorities();

    void updateTaskTypes(TaskTypesDTO taskTypesDTO);

    void updateTaskPriorities(TaskPrioritiesDTO taskPrioritiesDTO);

}
