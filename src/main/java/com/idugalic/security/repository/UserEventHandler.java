package com.idugalic.security.repository;

import com.idugalic.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author: idugalic
 * Date: 1/5/18
 * Time: 6:58 PM
 */
@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {


//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @HandleBeforeSave
    @HandleBeforeCreate
    public void handleUserSave(User user) {
//        String pass = user.getPassword();
//        if (pass != null) user.setPassword(this.passwordEncoder.encode(pass));
    }
}
