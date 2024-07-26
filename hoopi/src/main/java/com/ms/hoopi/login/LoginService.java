package com.ms.hoopi.login;

import com.ms.hoopi.join.UsersDto;

public interface LoginService {
    boolean validateUser(UsersDto user);
}
