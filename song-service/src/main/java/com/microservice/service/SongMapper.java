package com.microservice.service;

import com.microservice.domain.Song;
import com.microservice.domain.SongRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SongMapper implements Converter<Song, SongRecord> {

    @Override
    public SongRecord convert(Song song) {
        return SongRecord.builder()
                .songId(song.getSongId())
                .name(song.getName())
                .artist(song.getArtist())
                .album(song.getAlbum())
                .resourceId(song.getResourceId())
                .year(song.getYear())
                .length(song.getLength())
                .build();
    }

    public Song mapToEntity(SongRecord songRecord) {
        return Song.builder()
                .songId(songRecord.getResourceId())
                .name(songRecord.getName())
                .artist(songRecord.getArtist())
                .album(songRecord.getAlbum())
                .resourceId(songRecord.getResourceId())
                .year(songRecord.getYear())
                .length(songRecord.getLength())
                .build();
    }
}
