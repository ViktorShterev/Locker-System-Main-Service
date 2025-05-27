package my.projects.lockersystemusermicroservice.service;

import my.projects.lockersystemusermicroservice.dto.restDto.LockerLocationDTO;

import java.util.List;

public interface LockerService {

    List<LockerLocationDTO> getLockerLocations();
}
