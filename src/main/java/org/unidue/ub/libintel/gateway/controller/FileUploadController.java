package org.unidue.ub.libintel.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unidue.ub.libintel.gateway.exceptions.StorageFileNotFoundException;
import org.unidue.ub.libintel.gateway.model.FileWithLink;
import org.unidue.ub.libintel.gateway.services.StorageService;

import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    private static Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files/{module}")
    public ResponseEntity<?> listUploadedFiles(@PathVariable("module") String module) {
        storageService.setModule(module);
        return ResponseEntity.ok(storageService.showPrivateFiles().map(
                path -> new FileWithLink(path.getFileName().toString(), MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString(),module).build().toString() ))
                .collect(Collectors.toList()));
    }

    @GetMapping("/files/public/{module}")
    public ResponseEntity<?> listUploadedPublicFiles(@PathVariable("module") String module) {
        storageService.setModule(module);
        return ResponseEntity.ok(storageService.showPublicFiles().map(
                path -> new FileWithLink(path.getFileName().toString(), MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString(),module).build().toString() ))
                .collect(Collectors.toList()));
    }

    @GetMapping("/files/{module}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, @PathVariable("module") String module) {
        storageService.setModule(module);
        Resource file = storageService.loadPrivateAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/public/{module}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> servePublicFile(@PathVariable String filename, @PathVariable("module") String module) {
        storageService.setModule(module);
        Resource file = storageService.loadPublicAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/files/{module}")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("module") String module) {
        storageService.setModule(module);
        storageService.storePrivate(file);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/publicFiles/{module}")
    public String handlePublicFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("module") String module,
                                   RedirectAttributes redirectAttributes) {
        storageService.setModule(module);
        storageService.storePublic(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @DeleteMapping("/files/{module}/{filename:.+}")
    public ResponseEntity<?> deletFile(@PathVariable String filename, @PathVariable("module") String module) {
        storageService.setModule(module);
        try {
            boolean deleted = storageService.deletePrivateFile(filename);
            if (deleted) {
                log.info("deleted file " + filename);
                return ResponseEntity.accepted().build();
            }
            else {
                log.warn("could not delete file " + filename);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.warn("could not find file " + filename);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/files/public/{module}/{filename:.+}")
    public ResponseEntity<?> deletPublicFile(@PathVariable String filename, @PathVariable("module") String module) {
        storageService.setModule(module);
        try {
            boolean deleted = storageService.deletePublicFile(filename);
            if (deleted) {
                log.info("deleted file " + filename);
                return ResponseEntity.accepted().build();
            }
            else {
                log.warn("could not delete file " + filename);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.warn("could not find file " + filename);
            return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
