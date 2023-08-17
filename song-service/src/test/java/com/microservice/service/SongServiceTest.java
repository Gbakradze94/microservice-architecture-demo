package com.microservice.service;


import com.microservice.model.Song;
import com.microservice.model.SongMetaData;
import com.microservice.model.SongRecord;
import com.microservice.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {
    @Mock
    private SongRepository songRepository;

    @Mock
    private SongMapper songMapper;

    private SongService songService;

    @BeforeEach
    void setup() {
        songService = new SongServiceImpl(songRepository, songMapper);
    }

    @Test
    void shouldSaveSong() {
        Song song = Song.builder()
                .resourceId(1)
                .name("Speed King")
                .length("07:20")
                .artist("Deep Purple")
                .build();

        when(songMapper.mapToEntity(any())).thenReturn(song);
        when(songRepository.save(any())).thenReturn(song);
        SongMetaData songMetaData = SongMetaData.builder()
                .songId(1)
                .resourceId(1)
                .name("Speed King")
                .length("07:20")
                .artist("Deep Purple")
                .build();
        SongRecord songRecord = songService.save(songMetaData);
        assertNotNull(songRecord);

        verify(songMapper, times(1)).mapToEntity(any());
        verify(songRepository, times(1)).save(any());
    }
}
