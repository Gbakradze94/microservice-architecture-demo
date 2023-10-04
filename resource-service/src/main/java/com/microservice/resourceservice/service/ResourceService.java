package com.microservice.resourceservice.service;

import com.microservice.resourceservice.domain.Resource;
import com.microservice.resourceservice.domain.ResourceResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface ResourceService {
   ResourceResponse saveResource(MultipartFile multipartFile) throws IOException;

   Resource getResource(Long id);

   List<ResourceResponse> deleteByIds(int[] ids);

   String downloadFile(String id);
}
