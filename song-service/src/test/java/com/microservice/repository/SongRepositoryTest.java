package com.microservice.repository;

import com.microservice.controller.util.PostgresExtension;
import com.microservice.model.Song;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(PostgresExtension.class)
@DirtiesContext
@TestPropertySource(locations = "classpath:application.properties")
public class SongRepositoryTest {
    @Autowired
    private SongRepository songRepository;

    @Test
    void shouldSaveSong() {
        Song song = Song.builder()
                .resourceId(1)
                .name("Speed King")
                .length("07:20")
                .artist("Deep Purple")
                .build();
        Song result = songRepository.save(song);
        assertNotNull(result);
//        assertTrue(song.getId() > 0L);
//        assertEquals(song.getResourceId(), result.getResourceId());
//        assertEquals(song.getName(), result.getName());
//        assertEquals(song.getLength(), result.getLength());
//        assertNotNull(song.getCreatedDate());
//        assertNotNull(song.getLastModifiedDate());
    }
}
