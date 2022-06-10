package com.gui.tools.lsfs.util;

import com.gui.tools.lsfs.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyGenerator {
    // TODO: implement generator keys here

    public static String generateSecretKey(User user) {
        return "Secret key";
    }
}
