package com.microservice.resourceprocessor.domain.context;

import com.microservice.resourceprocessor.domain.dto.GetSongDTO;
import com.microservice.resourceprocessor.domain.dto.SaveSongDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.File;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@With
public class ResourceProcessingContext {
  private long resourceId;
  private File resourceFile;
  private SaveSongDTO saveSongDTO;
  private GetSongDTO getSongDTO;
}
