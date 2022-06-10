package com.gui.tools.lsfs.service;

import com.gui.tools.lsfs.model.User;

public interface UserService {

    User get(String userName);

    String save(User user);

    void delete();
}
