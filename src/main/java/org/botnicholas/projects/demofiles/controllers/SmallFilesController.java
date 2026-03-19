package org.botnicholas.projects.demofiles.controllers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @see <a href='https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/MIME_types/Common_types'>Content Types</a>
 */

@Slf4j
@RestController()
@RequestMapping("/api")
public class SmallFilesController {
//    FILES


    /**
     * This text file will be previewed.
     */
    @SneakyThrows
    @GetMapping(value = "/files/text/inline", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Resource> getTextFileInlineResponse() {
        log.info("Requested an inline Text file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/text.txt")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_TEXT_FILE.txt").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This text file will be downloaded.
     */
    @SneakyThrows
    @GetMapping(value = "/files/text/attachment", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Resource> getTextFileAttachmentResponse() {
        log.info("Requested an attachment Text file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/text.txt")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_TEXT_FILE.txt").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This pdf file will be previewed.
     */
    @SneakyThrows
    @GetMapping(value = "/files/pdf/inline", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPdfFileInlineResponse() {
        log.info("Requested an inline PDF file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/cat-care-guide.pdf")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_PDF_FILE.pdf").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This pdf text file will be downloaded.
     */
    @SneakyThrows
    @GetMapping(value = "/files/pdf/attachment", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPdfFileAttachmentResponse() {
        log.info("Requested an attachment PDF file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/cat-care-guide.pdf")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_PDF_FILE.pdf").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This png file will be previewed.
     */
    @SneakyThrows
//    @GetMapping(value = "/files/png/inline", produces = MediaType.IMAGE_PNG_VALUE)
    @GetMapping(value = "/files/png/inline") // In case we don't know the exact content type - like it could be PNG, JPG, JPEG etc. we just omit the produces and rely on the content type we set in the controller method
    public ResponseEntity<Resource> getPngFileInlineResponse() {
        log.info("Requested an inline png file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/img.png")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_PNG_FILE.png").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This png text file will be downloaded.
     */
    @SneakyThrows
//    @GetMapping(value = "/files/png/attachment", produces = MediaType.IMAGE_PNG_VALUE)
    @GetMapping(value = "/files/png/attachment")
    public ResponseEntity<Resource> getPngFileAttachmentResponse() {
        log.info("Requested an attachment png file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/img.png")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_PNG_FILE.png").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This jpeg file will be previewed.
     */
    @SneakyThrows
//    @GetMapping(value = "/files/jpeg/inline", produces = MediaType.IMAGE_JPEG_VALUE)
    @GetMapping(value = "/files/jpeg/inline") // In case we don't know the exact content type - like it could be PNG, JPG, JPEG etc. we just omit the produces and rely on the content type we set in the controller method
    public ResponseEntity<Resource> getJpegFileInlineResponse() {
        log.info("Requested an inline jpeg file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/img_1.jpeg")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_JPEG_FILE.jpeg").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This jpeg text file will be downloaded.
     */
    @SneakyThrows
//    @GetMapping(value = "/files/jpeg/attachment", produces = MediaType.IMAGE_JPEG_VALUE)
    @GetMapping(value = "/files/jpeg/attachment")
    public ResponseEntity<Resource> getJpegFileAttachmentResponse() {
        log.info("Requested an attachment jpeg file");

        var body = new FileSystemResource(Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/img_1.jpeg")).toURI()));
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_JPEG_FILE.jpeg").build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(body.getFilePath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }
}