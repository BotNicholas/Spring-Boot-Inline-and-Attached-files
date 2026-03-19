package org.botnicholas.projects.demofiles.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import tools.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @see <a href='https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/MIME_types/Common_types'>Content Types</a>
 */

@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping("/api")
public class BigAndGeneratedFilesController {
    private final ObjectMapper mapper;

// Big Files or Files built on flight

//    Here Content-Disposition just tells the client how to behave with the file
//    produces restricts the client accept
//    content-type tells what file is returned
//    StreamingResponseBody helps you to transfer big files


    /**
     * This big text file will be previewed.
     */
    @SneakyThrows
//    produces helps spring to understand the Content type to return. Also it restricts user to pass anything in the accept.
//    @GetMapping(value = "/big/files/text/inline", produces = MediaType.TEXT_PLAIN_VALUE) // ONLY if you know that it's a text
//    @GetMapping(value = "/big/files/text/inline", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) // Default value - in case you don't know the content type
    @GetMapping(value = "/big/files/text/inline") // Usually with StreamingResponseBody you control the content type, not Spring, so you can set the content type by yourself
    public ResponseEntity<StreamingResponseBody> getBigTextFileInlineResponse() {
        log.info("Requested a big inline Text file");

        var file = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/big.txt")).toURI());
        var fileInputStream = new FileInputStream(file);

        StreamingResponseBody stream = outputStream -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try(fileInputStream) {
                fileInputStream.transferTo(outputStream);
                outputStream.flush();
//                outputStream.write(fileInputStream.readAllBytes());
            }
        };

        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename(file.getName()).build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(file.toPath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(stream);
    }

    /**
     * This big text file will be downloaded.
     */
    @SneakyThrows
    @GetMapping(value = "/big/files/text/attachment", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<StreamingResponseBody> getBigTextFileAttachmentResponse() {
        log.info("Requested a big attachment Text file");

        var file = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/big.txt")).toURI());
        var fileInputStream = new FileInputStream(file);

        StreamingResponseBody stream = outputStream -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try(fileInputStream) {
                outputStream.write(fileInputStream.readAllBytes());
            }
        };

        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(file.toPath())));

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(stream);
    }


    /**
     * This GENERATED big text file will be previewed.
     * Browser will wait until it's all returned
     */
    @SneakyThrows
//    @GetMapping(value = "/big/generated/files/text/inline", produces = MediaType.TEXT_PLAIN_VALUE) // If you don't specify the content type manually you won't see this header, so this produces here is useless
    @GetMapping(value = "/big/generated/files/text/inline")
    public ResponseEntity<StreamingResponseBody> getBigGeneratedTextFileInlineResponse() {
        log.info("Requested a big generated inline Text file");

        StreamingResponseBody stream = outputStream -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                outputStream.write(("line " + i + "\n").getBytes());
                outputStream.flush();
            }
        };

        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_BIG_GENERATED_TEXT_FILE.txt").build());
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(stream);
    }

    /**
     * This GENERATED big text file will be downloaded.
     * Browser will wait until it's all returned
     */
    @SneakyThrows
    @GetMapping(value = "/big/generated/files/text/attachment")
    public ResponseEntity<StreamingResponseBody> getBigGeneratedTextFileAttachmentResponse() {
        log.info("Requested a big generated attachment Text file");

        int chunkSize = 10;

        StreamingResponseBody stream = outputStream -> {
            int chunk = 0;
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                outputStream.write(("line " + i + "\n").getBytes());
                chunk++;
                if (chunkSize == chunk) {
                    outputStream.flush();
                    chunk=0;
                }
            }
        };
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_BIG_GENERATED_TEXT_FILE.txt").build());
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(stream);
    }


    /**
     * This GENERATED big JSON file will be previewed.
     * Browser will wait until it's all returned
     */
    @SneakyThrows
    @GetMapping(value = "/big/generated/files/json/inline")
    public ResponseEntity<StreamingResponseBody> getBigGeneratedJsonFileInlineResponse() {
        log.info("Requested a big generated inline Json file");

        var hugeObject = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            hugeObject.put("key" + i, i);
        }

        StreamingResponseBody stream = outputStream -> {
            mapper.writeValue(outputStream, hugeObject);
        };

        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_BIG_GENERATED_JSON_FILE.json").build());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(stream);
    }

    /**
     * This GENERATED big JSON file will be downloaded.
     * Browser will wait until it's all returned
     */
    @SneakyThrows
    @GetMapping(value = "/big/generated/files/json/attachment")
    public ResponseEntity<StreamingResponseBody> getBigGeneratedJsonFileAttachmentResponse() {
        log.info("Requested a big generated attachment Json file");

        var hugeObject = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            hugeObject.put("key" + i, i);
        }

        StreamingResponseBody stream = outputStream -> {
            mapper.writeValue(outputStream, hugeObject);
        };

        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_BIG_GENERATED_JSON_FILE.json").build());
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(stream);
    }

    /**
     * @see <a href='https://testdatahub.com/generate_files'>Here you can generate dummy files like the 100MB one</a>
     */
    //Here I return a huge txt file of 100mb size. For such huge files you have to use StreamingResponseBody and write with chunks!
    @SneakyThrows
    @GetMapping(value = "/big/files/text/100mb/attachment")
    public ResponseEntity<StreamingResponseBody> getBigTextFile500mdAttachmentResponse() {

        var file = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("static/fakefile_100MB.txt")).toURI());

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(file.getName()).build());
        headers.setContentType(MediaType.valueOf(Files.probeContentType(file.toPath())));
        headers.setContentLength(file.length());

        StreamingResponseBody stream = outputStream -> {
            try (InputStream inputStream = new FileInputStream(file)) {
//                byte[] buffer = new byte[500 * 1024 * 1024]; //500MB -> causes in huge server memory usage. 100 users == ☠️
//                byte[] buffer = new byte[4 * 1024 * 1024]; //4MB -> still pretty big
                byte[] buffer = new byte[64 * 1024]; //64KB
//                byte[] buffer = new byte[4 * 1024]; //4KB -> too small. Leads low memory usage but increases CPU usage.

                // bytesRead is required because read(buffer) may fill only part of the buffer;
                // we must write only the bytes actually read
                int bytesRead;

                while((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                //sending all the data
                outputStream.flush();
            }
        };

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(stream);
    }
}