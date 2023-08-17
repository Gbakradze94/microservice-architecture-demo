package com.microservice.service;

import com.microservice.exception.SongNotFoundException;
import com.microservice.model.Song;
import com.microservice.model.SongMetaData;
import com.microservice.model.SongRecord;
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
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SongMapper songMapper;


    public SongRecord save(SongMetaData songMetaData) {
        Song song = songRepository.save(songMapper.mapToEntity(songMetaData));
        return SongRecord.builder()
                .songId(song.getSongId())
                .build();
    }

    public SongMetaData getSongById(Long id) {
        Song song = songRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new SongNotFoundException("Song with " + id + "could not be found"));
        return songMapper.convert(song);
    }

    public List<SongRecord> deleteByIds(int[] ids) {

        Arrays.stream(ids)
                .mapToObj(id -> songRepository.getOne(Integer.parseInt(String.valueOf(id))))
                .forEach(songRepository::delete);

        return Arrays.stream(ids)
                .mapToObj(id -> SongRecord.builder()
                        .songId(Integer.parseInt(String.valueOf(id)))
                        .build()
                )
        .collect(Collectors.toList());
    }
}
