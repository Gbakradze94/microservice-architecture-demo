package com.microservice.service;

import com.microservice.domain.Song;
import com.microservice.domain.SongRecord;
import com.microservice.domain.SongRecordId;
import com.microservice.exception.SongNotFoundException;
import com.microservice.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongService {


    private final SongRepository songRepository;
    private final SongMapper songMapper;

    public SongRecordId save(SongRecord songRecord) {
        Song song = songRepository.save(songMapper.mapToEntity(songRecord));
        log.info("ID: " + song.getSongId());
        return new SongRecordId(song.getSongId());
    }

    public SongRecord getSongById(Long id) {
        Song song = songRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new SongNotFoundException("Song with " + id + "could not be found"));
        return songMapper.convert(song);
    }

    public List<SongRecordId> deleteByIds(int[] ids) {

        Arrays.stream(ids)
                .mapToObj(id -> songRepository.getOne(Integer.parseInt(String.valueOf(id))))
                .forEach(songRepository::delete);

        return Arrays.stream(ids)
                .mapToObj(id -> new SongRecordId(Integer.parseInt(String.valueOf(id))))
        .collect(Collectors.toList());
    }
}
