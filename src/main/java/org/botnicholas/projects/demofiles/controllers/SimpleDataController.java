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
import java.util.Map;
import java.util.Objects;

/**
 * @see <a href='https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/MIME_types/Common_types'>Content Types</a>
 */

@Slf4j
@RestController()
@RequestMapping("/api")
public class SimpleDataController {
//    INLINE DATA

    /**
     * This endpoint returns a json that is displayed in the browser.
     *
     * * For Object, String, DTO Spring will detect content type on his own. produces here just restricts clients accept header values
     * * For Resource Spring will use ResourceHttpMessageConverter and it can try to determine the content type from the resource, but often it fallbacks to application/octet-stream.
     *   This way you need to specify the contentType just so it is treated correctly, and not like the default octet stream.
     *   The produces here again just restricts the user accept values
     * * For the StreamingResponseBody Spring won't set the content type at all, so you have to specify the content type yourself.
     *   Here produces is often omitted, since it's pretty useless.
     *
     * Produces restricts client accept header values, helps Spring in content negotiation, helps in message converter selection and improves the API contract.
     */
    @GetMapping(value = "/json/inline", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getJsonPreviewResponse() {
        log.info("Requested an inline JSON");

        var body = Map.<String, Object>of("key", "value");
        var headers = new HttpHeaders();
//        By default browser decided the contend disposition. But here's the way we can explicitly say what to do with this data
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_JSON.json").build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This endpoint returns a json that is downloaded.
     */
    @GetMapping(value = "/json/attachment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getJsonAttachmentResponse() {
        log.info("Requested an attachment JSON");

        var body = Map.<String, Object>of("key", "value");
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_JSON.json").build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * The same for a usual Strings.
     */
    @GetMapping(value = "/text/inline", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getTextPreviewResponse() {
        log.info("Requested an inline Text");

        var body = "Hello!";
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_TEXT.txt").build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This string will be downloaded.
     */
    @GetMapping(value = "/text/attachment", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getTextAttachmentResponse() {
        log.info("Requested an attachment Text");

        var body = "Hello!";
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_TEXT.txt").build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * The same for an HTML.
     */
    @GetMapping(value = "/html/inline", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getHtmlPreviewResponse() {
        log.info("Requested an inline HTML");

        var body = "<h1 style='color:red'>Hello!</h1>";
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.inline().filename("INLINE_HTML.html").build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }

    /**
     * This HTML will be downloaded.
     */
    @GetMapping(value = "/html/attachment", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getHtmlAttachmentResponse() {
        log.info("Requested an attachment HTML");

        var body = "<h1 style='color:red'>Hello!</h1>";
        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("ATTACHMENT_HTML.html").build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(body);
    }
}