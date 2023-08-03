package com.microservice.controller;

import com.microservice.model.SongRecord;
import com.microservice.model.SongRecordId;
import com.microservice.service.SongService;
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

    private final SongService songService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongRecordId save(@RequestBody SongRecord songRecord) {
        log.info("Saving a song metadata '{}'", songRecord);
        return songService.save(songRecord);
    }

    @GetMapping("/{id}")
    public SongRecord getSong(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @DeleteMapping
    public List<SongRecordId> deleteSongs(@RequestParam("id") int[] ids) {
        return songService.deleteByIds(ids);
    }
}
