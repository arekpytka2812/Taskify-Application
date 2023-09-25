package com.pytka.taskifyapplication.services;

import java.util.List;

public interface RequestService {

    <RES> RES getRequest(Class<RES> responseType, String endpointURI);

    <RES> List<RES> getListRequest(Class<RES> responseType, String endpointURI);

    <RES, REQ> RES postRequest(Class<RES> responseType, REQ request, String endpointURI);

    <RES> RES deleteRequest(Class<RES> responseType, String endpointURI);

}
