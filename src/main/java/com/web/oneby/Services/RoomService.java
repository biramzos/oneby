package com.web.oneby.Services;

import com.web.oneby.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private RoomRepository roomRepository;

    @Autowired
    public RoomService (
            RoomRepository roomRepository
    ) {
        this.roomRepository = roomRepository;
    }
}
