package com.microservice.service;

import com.microservice.model.Song;
import com.microservice.model.SongMetaData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SongMapper implements Converter<Song, SongMetaData> {

    @Override
    public SongMetaData convert(Song song) {
        return SongMetaData.builder()
                .songId(song.getSongId())
                .name(song.getName())
                .artist(song.getArtist())
                .album(song.getAlbum())
                .resourceId(song.getResourceId())
                .year(song.getYear())
                .length(song.getLength())
                .build();
    }

    public Song mapToEntity(SongMetaData songMetaData) {
        return Song.builder()
                .songId(songMetaData.getResourceId())
                .name(songMetaData.getName())
                .artist(songMetaData.getArtist())
                .album(songMetaData.getAlbum())
                .resourceId(songMetaData.getResourceId())
                .year(songMetaData.getYear())
                .length(songMetaData.getLength())
                .build();
    }
}
