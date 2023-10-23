package com.pytka.taskifyapplication.core.service.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.models.TaskPrioritiesDTO;
import com.pytka.taskifyapplication.core.models.TaskTypesDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;
import com.pytka.taskifyapplication.core.service.RequestService;
import com.pytka.taskifyapplication.core.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final RequestService requestService;

    @Override
    public UserSettingsDTO getUserSettings() {

        String endpointURI = "/settings/" + SpringMainApplication.USER_ID;

        return this.requestService.getRequest(UserSettingsDTO.class, endpointURI);
    }

    @Override
    public TaskTypesDTO getTaskTypes() {

        String endpointURI = "/settings/types/" + SpringMainApplication.USER_ID;

        return this.requestService.getRequest(TaskTypesDTO.class, endpointURI);
    }

    @Override
    public TaskPrioritiesDTO getTaskPriorities() {

        String endpointURI = "/settings/priorities/" + SpringMainApplication.USER_ID;

        return this.requestService.getRequest(TaskPrioritiesDTO.class, endpointURI);
    }

    @Override
    public void updateTaskTypes(TaskTypesDTO taskTypesDTO) {

        String endpointURI = "/settings/types/" + SpringMainApplication.USER_ID;

        this.requestService.postRequest(Void.class, taskTypesDTO, endpointURI);
    }

    @Override
    public void updateTaskPriorities(TaskPrioritiesDTO taskPrioritiesDTO) {

        String endpointURI = "/settings/priorities/" + SpringMainApplication.USER_ID;

        this.requestService.postRequest(Void.class, taskPrioritiesDTO, endpointURI);
    }
}
