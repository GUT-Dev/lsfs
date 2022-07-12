package com.gut.tools.lsfs.service.file;

import com.gut.tools.lsfs.model.user.User;

public interface UserService {

    User get(String userName);

    String save(User user);

    void delete();
}
