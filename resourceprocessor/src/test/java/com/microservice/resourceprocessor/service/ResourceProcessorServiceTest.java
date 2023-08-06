package com.microservice.resourceprocessor.service;

import com.microservice.resourceprocessor.client.ResourceServiceClient;
import com.microservice.resourceprocessor.client.SongServiceClient;
import com.microservice.resourceprocessor.domain.ResourceRecord;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResourceProcessorServiceTest {

    public static final String BASE_MP3_FILE_PATH = "C:\\Users\\Giorgi_Bakradze\\IdeaProjects\\microservices-architecture-overview\\resource-service\\src\\main\\resources\\files\\";
    @Mock
    private ResourceServiceClient resourceServiceClient;

    @Mock
    private SongServiceClient songServiceClient;

    @Mock
    private Mp3Parser mp3Parser;
    @Mock
    private Metadata metadata;
    @Mock
    private ParseContext parseContext;
    @Mock
    private BodyContentHandler bodyContentHandler;


    private ResourceProcessorService resourceProcessorService;

    @BeforeEach
    public void setup() {
        resourceProcessorService = new ResourceProcessorService(
                mp3Parser,
                metadata,
                parseContext,
                bodyContentHandler,
                resourceServiceClient,
                songServiceClient
        );
    }

    @Test
    void whenCalled_shouldProcessResource() throws IOException, TikaException, SAXException {
        ResourceRecord resourceRecord = ResourceRecord.builder().id(1L).build();
        File testFile = initializeTestFile();

        when(resourceServiceClient.getById(resourceRecord.getId()))
                .thenReturn(Optional.of(testFile));

        boolean isFileProcessed = resourceProcessorService.processResource(resourceRecord);
        assertTrue(isFileProcessed);
    }

    private File initializeTestFile() throws IOException {
        return ResourceUtils.getFile("src/test/resources/files/sample-12s.mp3");
    }
}
