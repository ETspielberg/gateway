package org.unidue.ub.libintel.gateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.unidue.ub.libintel.gateway.exceptions.StorageException;
import org.unidue.ub.libintel.gateway.exceptions.StorageFileNotFoundException;

import javax.annotation.security.RolesAllowed;
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
    @RolesAllowed("ROLE_ADMIN")
    public boolean storePublic(MultipartFile file) {
        setRootLocation(false);
        return storeFile(file);
    }

    @Override
    public boolean storePrivate(MultipartFile file) {
        setRootLocation(true);
        return storeFile(file);
    }

    @Override
    public Stream<Path> showPrivateFiles() {
        setRootLocation(true);
        return this.showFiles();
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public Stream<Path> showPublicFiles() {
        setRootLocation(false);
        return this.showFiles();
    }

    @Override
    public Path loadPrivate(String filename) {
        setRootLocation(true);
        return rootLocation.resolve(filename);
    }

    @Override
    public Path loadPublic(String filename) {
        setRootLocation(false);
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadPublicAsResource(String filename) {
        setRootLocation(false);
        return loadAsResource(filename);

    }

    @Override
    public Resource loadPrivateAsResource(String filename) {
        setRootLocation(true);
        return this.loadAsResource(filename);
    }

    @Override
    @RolesAllowed("ROLE_ADMIN")
    public boolean deletePublicFile(String filename) {
        Path path = loadPublic(filename);
        return path.toFile().delete();
    }

    @Override
    public boolean deletePrivateFile(String filename) {
        Path path = loadPrivate(filename);
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

    private void setRootLocation(boolean isPrivate) {
        String username = "public";
        if (isPrivate)
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        rootLocation = Paths.get(datadir + "/" + username + "/" + module);
        if (!rootLocation.toFile().exists())
            rootLocation.toFile().mkdirs();
    }

    private Stream<Path> showFiles() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException ioe) {
            log.error("failed to read stored files", ioe);
            return null;
        }
    }

    private boolean storeFile(MultipartFile file) {
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

    private Resource loadAsResource(String filename) {
        try {
            Path file = loadPublic(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            log.error("could not read file: " + filename, e);
            return null;
        }
    }

}
