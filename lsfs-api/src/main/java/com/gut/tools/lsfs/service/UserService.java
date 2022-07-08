package com.gut.tools.lsfs.service;

import com.gut.tools.lsfs.model.User;

public interface UserService {

    User get(String userName);

    String save(User user);

    void delete();
}
