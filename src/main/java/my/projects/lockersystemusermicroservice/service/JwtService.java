package my.projects.lockersystemusermicroservice.service;

import java.util.Map;

public interface JwtService {
    String generateToken(String userId, Map<String, Object> claims);
}
