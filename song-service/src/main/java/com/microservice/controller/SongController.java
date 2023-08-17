package com.microservice.controller;

import com.microservice.model.SongMetaData;
import com.microservice.model.SongRecord;
import com.microservice.service.SongServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/songs")
@RequiredArgsConstructor
@Slf4j
public class SongController {

    private final SongServiceImpl songServiceImpl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongRecord save(@RequestBody SongMetaData songMetaData) {
        log.info("Saving a song metadata '{}'", songMetaData);
        return songServiceImpl.save(songMetaData);
    }

    @GetMapping("/{id}")
    public SongMetaData getSong(@PathVariable Long id) {
        return songServiceImpl.getSongById(id);
    }

    @DeleteMapping
    public List<SongRecord> deleteSongs(@RequestParam("id") int[] ids) {
        return songServiceImpl.deleteByIds(ids);
    }
}
