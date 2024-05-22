package com.ssafy.user.model.service;

import com.ssafy.user.dto.*;

public interface UserService {

    UserInfoDto getUserInfo(String id);

    void logout(String userId);

    void changeUserInfo(String userId, ChangeRequestDto changeRequestDto);

    void deleteUser(String userId);

}
