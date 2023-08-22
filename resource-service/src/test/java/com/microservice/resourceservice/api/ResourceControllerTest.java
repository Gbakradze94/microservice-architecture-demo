package com.microservice.resourceservice.api;

import com.microservice.resourceservice.domain.Resource;
import com.microservice.resourceservice.service.ResourceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(value = {
        MockitoExtension.class
})
public class ResourceControllerTest {

    @InjectMocks
    private ResourceController resourceController;

    @Mock
    private ResourceServiceImpl resourceServiceImpl;

    @Test
    void getResource_shouldCallResourceService() {
        Resource resource = Resource.builder()
                .id(1)
                .name("")
                .build();

        when(resourceServiceImpl.getResource(anyLong())).thenReturn(resource);

        resourceController.getResource(1L);

        verify(resourceServiceImpl, times(1)).getResource(1L);
    }

    @Test
    void saveResource_shouldCallResourceService() throws IOException, SAXException {
        // given
        File file = ResourceUtils.getFile("classpath:files/sample-12s.mp3");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("multipartFile", file.getName(),
                "audio/mpeg", Files.readAllBytes(file.toPath()));

        // when
        resourceServiceImpl.saveResource(mockMultipartFile);

        // then
        verify(resourceServiceImpl, times(1)).saveResource(mockMultipartFile);
    }
}
