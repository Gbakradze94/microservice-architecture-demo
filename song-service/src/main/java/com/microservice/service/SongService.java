package com.microservice.service;

import com.microservice.exception.SongNotFoundException;
import com.microservice.model.Song;
import com.microservice.model.SongMetaData;
import com.microservice.model.SongRecord;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface SongService {
    SongRecord save(SongMetaData songMetaData);
    SongMetaData getSongById(Long id);

    List<SongRecord> deleteByIds(int[] ids);
}
