package com.ms.hoopi.auth.controller;

public interface RedisService{

    public void saveRfrToken(String id, String token);

    public void saveAcsToken(String id, String token);

    public String getRfrToken(String id);

    public String getAcsToken(String id);

    public void deleteJwtToken(String id);

}
