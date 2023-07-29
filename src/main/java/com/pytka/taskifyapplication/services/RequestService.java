package com.pytka.taskifyapplication.services;

public interface RequestService {

    <RES> RES getRequest(Class<RES> responseType, String endpointURI);

    <RES, REQ> RES postRequest(Class<RES> responseType, REQ request, String endpointURI);

    <RES> RES deleteRequest(Class<RES> responseType, String endpointURI);

}
