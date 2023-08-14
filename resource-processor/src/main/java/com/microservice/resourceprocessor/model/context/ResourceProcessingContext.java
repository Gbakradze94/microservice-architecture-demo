package com.microservice.resourceprocessor.model.context;

import com.microservice.resourceprocessor.model.SongMetaData;
import com.microservice.resourceprocessor.model.dto.GetSongDTO;
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
  private Long resourceId;
  private File resourceFile;
  private SongMetaData songMetaData;
  private GetSongDTO getSongDTO;
}
