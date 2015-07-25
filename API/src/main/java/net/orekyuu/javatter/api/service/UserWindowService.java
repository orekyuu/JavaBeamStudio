package net.orekyuu.javatter.api.service;

import net.orekyuu.javatter.api.twitter.model.User;

@Service
public interface UserWindowService {

    void open(User user);
}
