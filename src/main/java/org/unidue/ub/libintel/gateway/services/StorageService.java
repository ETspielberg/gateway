package org.unidue.ub.libintel.gateway.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    boolean storePrivate(MultipartFile file);

    boolean storePublic(MultipartFile file);

    Stream<Path> showPublicFiles();

    Stream<Path> showPrivateFiles();

    Path loadPublic(String filename);

    Path loadPrivate(String filename);

    Resource loadPublicAsResource(String filename);

    Resource loadPrivateAsResource(String filename);

    boolean deletePublicFile(String filename);

    boolean deletePrivateFile(String filename);

    void setModule(String module);

}
