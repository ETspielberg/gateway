package org.unidue.ub.libintel.gateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.unidue.ub.libintel.gateway.exceptions.StorageException;
import org.unidue.ub.libintel.gateway.exceptions.StorageFileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    // read the configuration properties to set the local storage
    @Value("${ub.statistics.data.dir}")
    private String datadir;

    private Path rootLocation;

    private String module = "";

    public void setModule(String module) {
        this.module = module;
        this.rootLocation = Paths.get(this.datadir + "/" + module);
    }

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean store(MultipartFile file) {
        rootLocation = Paths.get(datadir + "/" + module);
        if (!rootLocation.toFile().exists())
            rootLocation.toFile().mkdirs();
        if (file == null)
            return false;
        if (file.getOriginalFilename() == null)
            return false;
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("storing " + filename + " to " + rootLocation.toString());
        try {
            if (file.isEmpty()) {
                log.warn("empty file");
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                log.warn("cannot Store file at given directory");
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            log.debug("saved file");
            return true;
        } catch (IOException e) {
            log.warn("failed to store file");
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        rootLocation = Paths.get(datadir + "/" + module);
        if (!rootLocation.toFile().exists())
            rootLocation.toFile().mkdir();
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        rootLocation = Paths.get(datadir + "/" + module);
        if (!rootLocation.toFile().exists())
            rootLocation.toFile().mkdirs();
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public boolean deleteFile(String filename) {
        rootLocation = Paths.get(datadir + "/" + module);
        Path path = load(filename);
        return path.toFile().delete();
    }

    @Override
    public void init() {
        try {
            rootLocation = Paths.get(datadir + "/" + module);
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
