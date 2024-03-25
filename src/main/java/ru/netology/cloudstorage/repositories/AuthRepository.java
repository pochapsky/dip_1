package ru.netology.cloudstorage.repositories;

import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.models.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@Repository
public class AuthRepository {

    private static final Map<String, User> mapAuthenticationUsers = new ConcurrentHashMap<>();

    public void saveAuthenticationUser(String authToken, User user) {
        mapAuthenticationUsers.put(authToken, user);
    }

    public void deleteAuthenticationUserByToken(String authToken) {
        mapAuthenticationUsers.remove(authToken);
    }

    public User getAuthenticationUserByToken(String authToken) {
        return mapAuthenticationUsers.get(authToken);
    }
}

