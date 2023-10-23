package com.pytka.taskifyapplication.core.service.impl;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.core.models.StatsDTO;
import com.pytka.taskifyapplication.core.service.RequestService;
import com.pytka.taskifyapplication.core.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final RequestService requestService;

    @Override
    public StatsDTO getUserStats(){

        String endpointURI = "/stats/" + SpringMainApplication.USER_ID;

        return this.requestService.getRequest(StatsDTO.class, endpointURI);
    }
}
