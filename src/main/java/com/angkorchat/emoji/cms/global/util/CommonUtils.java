package com.angkorchat.emoji.cms.global.util;

import com.angkorchat.emoji.cms.domain.studio.artist.exception.InvalidAccessException;
import com.angkorchat.emoji.cms.global.config.security.util.SecurityUtils;
import com.angkorchat.emoji.cms.global.error.ApiRequestErrorException;
import com.angkorchat.emoji.cms.global.error.handler.RestResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private static final List<String> codeList = List.of("code", "errorCode", "status");
    private static final List<String> messageList = List.of("message", "errorMessage", "description");

    // 임시 비밀번호 생성(암호화 포함)
    public static String getRandomPassword(int pwLength) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "*!@%^&-=";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;

        Random random = new Random();
        StringBuilder pw = new StringBuilder();
        pw.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        pw.append(capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length())));
        pw.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        pw.append(numbers.charAt(random.nextInt(numbers.length())));

        int idx;
        for (int i = 4; i < pwLength; i++) {
            idx = random.nextInt(combinedChars.length());
            pw.append(combinedChars.charAt(idx));
        }

        return pw.toString();
    }

    // pageable sort 지정값만 정해주고 나머지 제거
    public static Pageable validSort(Pageable pageable, String... validFields) {
        List<String> validList = List.of(validFields);

        List<Sort.Order> validOrders = pageable.getSort().stream()
                .filter(order -> validList.contains(order.getProperty()))
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(validOrders));
    }

    // pageable 에 기본 sort 값 설정 (이미 sort 가 있는 경우에는 변경하지 않음)
    public static Pageable setDefaultSort(Pageable pageable, Sort newSort) {
        if (pageable.getSort().isEmpty()) {
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
        } else {
            return pageable;
        }
    }

    // pageable 의 정렬 값을 검사하여 validOrder 와 일치할 경우 ChangeOrder 로 변경
    public static Pageable validSortChange(Pageable pageable, String changOrder, String validOrder) {
        List<Sort.Order> newOrders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            if (order.getProperty().equals(validOrder)) {
                newOrders.add(new Sort.Order(order.getDirection(), changOrder));
            } else {
                newOrders.add(order);
            }
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(newOrders));
    }

    // 외부 api 호출
    public static ResponseEntity<String> callApi(String url, HttpMethod method, Map<String, String> headersMap, Object request, Map<String, Object> exceptionMap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        restTemplate.setErrorHandler(new RestResponseErrorHandler());

        headers.setContentType(MediaType.APPLICATION_JSON);
        // 헤더 맵에서 커스텀 헤더 추가
        if (headersMap != null) {
            headersMap.forEach(headers::set);
        }

        // request 가 이미 JSON 문자열인지, 추가 처리가 필요한지 판단
        HttpEntity<?> entity;
        if (request instanceof String) {
            entity = new HttpEntity<>((String) request, headers);
        } else {
            entity = new HttpEntity<>(request, headers);
        }

        log.info("\n\nurl: {}\nheader: {}\nrequest: {}\n", url, headers, request);
        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
        log.info("\n\nresponse: {}\n", response);

        JsonNode jsonNode = CommonUtils.responseBodyToJsonNode(String.valueOf(response.getBody()));

        if (exceptionMap != null && exceptionMap.get("status").equals(response.getStatusCode())) {

            if (exceptionMap.get("code") == null || exceptionMap.get("code").equals(jsonNode.get("code").toString())) {

                try {
                    Class<?> exceptionClass = (Class<?>) exceptionMap.get("exception");

                    throw (Exception) exceptionClass.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (response.getStatusCode().is5xxServerError()) {
            throw new ApiRequestErrorException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        } else if (response.getStatusCode().is4xxClientError()) {

            if (jsonNode.has("errorMessage")) {
                throw new ApiRequestErrorException(jsonNode.get("errorCode").asInt() + " : " + jsonNode.get("errorMessage").asText());
            } else if (jsonNode.has("message")) {
                throw new ApiRequestErrorException(jsonNode.get("code").asInt() + " : " + jsonNode.get("message").asText());
            }
        }

        return response;
    }

    // 외부 api 호출
    public static <T> T callApi(String url, HttpMethod method, Map<String, String> headersMap, Object request, Map<String, Object> exceptionMap, ParameterizedTypeReference<T> responseClass) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        int code = 0;
        String message = "";

        restTemplate.setErrorHandler(new RestResponseErrorHandler());

        try {
            headers.setContentType(MediaType.valueOf("application/json"));
            // 헤더 맵에서 커스텀 헤더 추가
            if (headersMap != null) {
                headersMap.forEach(headers::set);
            }

            // request 가 이미 JSON 문자열인지, 추가 처리가 필요한지 판단
            HttpEntity<?> entity;
            if (request instanceof String) {
                entity = new HttpEntity<>((String) request, headers);
            } else {
                entity = new HttpEntity<>(request, headers);
            }

            log.info("\n\nurl: {}\nheader: {}\nrequest: {}\n", url, headers, request);
            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseClass);
            log.info("\n\nresponse: {}\n", response);

            if (response.getStatusCode().is5xxServerError()) {
                throw new ApiRequestErrorException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
            } else if (response.getStatusCode().is4xxClientError()) {
                JsonNode jsonNode = CommonUtils.responseBodyToJsonNode(String.valueOf(response.getBody()));
                if (jsonNode.has("errorMessage")) {
                    throw new ApiRequestErrorException(jsonNode.get("errorCode").asInt() + " : " + jsonNode.get("errorMessage").asText());
                } else if (jsonNode.has("message")) {
                    throw new ApiRequestErrorException(jsonNode.get("code").asInt() + " : " + jsonNode.get("message").asText());
                }
            }

            return response.getBody();
        } catch (RestClientResponseException e) {
            log.error("RestClientResponseException");
            try {
                String responseBody = e.getResponseBodyAsString();
                JSONObject jsonObject = new JSONObject(responseBody);
                code = Integer.parseInt(Objects.requireNonNull(getValueByKeys(jsonObject, codeList)));
                message = getValueByKeys(jsonObject, messageList);
                if (code > 0) {
                    throw new ApiRequestErrorException("RestClientResponseException => " + code + " : " + message);
                }
            } catch (JSONException ex) {
                log.error("Exception : {}", ex.getMessage());
                throw new RuntimeException(ex);
            }
        }

        throw new ApiRequestErrorException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

    // 외부 api 호출
    public static ResponseEntity<String> callApi(HttpMethod method, Map<String, String> headersMap, Object request, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        restTemplate.setErrorHandler(new RestResponseErrorHandler());

        headers.setContentType(MediaType.APPLICATION_JSON);
        // 헤더 맵에서 커스텀 헤더 추가
        if (headersMap != null) {
            headersMap.forEach(headers::set);
        }

        // request 가 이미 JSON 문자열인지, 추가 처리가 필요한지 판단
        HttpEntity<?> entity;
        if (request instanceof String) {
            entity = new HttpEntity<>((String) request, headers);
        } else {
            entity = new HttpEntity<>(request, headers);
        }

        log.info("openApi \n\nurl: {}\nheader: {}\nrequest: {}\n", url, headers, request);
        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
        log.info("openApi  \n\nresponse: {}\n", response);
        JsonNode jsonNode = CommonUtils.responseBodyToJsonNode(String.valueOf(response.getBody()));

        if (response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError()) {
            if (jsonNode.has("status_code")) {
                throw new ApiRequestErrorException(url + " : " +jsonNode.get("status_code").asText());
            } else {
                throw new ApiRequestErrorException(url + " : " + response.getBody());
            }
        }

        return response;
    }

    // ResponseEntity<T> 의 responseBody 추출
    public static JsonNode responseBodyToJsonNode(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);

            replaceNullsWithEmptyStrings(rootNode);

            return rootNode;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void replaceNullsWithEmptyStrings(JsonNode node) {
        if (node.isObject()) {
            // ObjectNode (JSON 객체) 의 경우
            ObjectNode objectNode = (ObjectNode) node;

            objectNode.fields().forEachRemaining(field -> {
                JsonNode childNode = field.getValue();

                if (childNode.isNull()) {
                    objectNode.put(field.getKey(), "");
                } else {
                    replaceNullsWithEmptyStrings(childNode);
                }
            });
        } else if (node.isArray()) {
            // ArrayNode (JSON 배열) 의 경우
            for (JsonNode childNode: node) {
                replaceNullsWithEmptyStrings(childNode);
            }
        }
    }

    public static String logRequest(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj); // JSON 문자열로 변환
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON while logging request", e);
            return "Error converting object to JSON while logging request";
        }
    }

    public static void sendAdminMessage(String authUrl, String chatAppKey, String chatAuthKey, String notiMessage, List<String> angkorIds) {
        for (String angkorId : angkorIds) {

            String notiUrl = authUrl + "/message/v1/chat";

            // Noti Chat 전송 Header
            Map<String, String> headerMap = new HashMap<>();
            Map<String, String> requestMap = new HashMap<>();
            Map<String, String> notiMessageMap = new HashMap<>();
            headerMap.put("AppKey", chatAppKey);
            headerMap.put("Authorization", chatAuthKey);
            requestMap.put("grantType", "session");
            requestMap.put("angkorId", angkorId);
            requestMap.put("messageType", "CHT");
            notiMessageMap.put("Type", "angkorgames");
            notiMessageMap.put("message", notiMessage);

            ObjectMapper objectMapperSender = new ObjectMapper();

            String senderNotiMessageJson;

            try {
                senderNotiMessageJson = objectMapperSender.writeValueAsString(notiMessageMap);
            } catch (JsonProcessingException e) {
                log.error("ERROR : ObjectMapper =>" + e);
                throw new RuntimeException(e);
            }

            requestMap.put("notiMessage", senderNotiMessageJson);

            CommonUtils.callApi(notiUrl, HttpMethod.POST, headerMap, requestMap, null);
        }
    }

    public static void infoLogging(String name) {

        log.info("지금 타고있는 흐름 : {}", name);
    }

    public static String getValueByKeys(JSONObject json, List<String> keys) throws JSONException {
        for (String key: keys) {
            if (json.has(key)) {
                return json.get(key).toString();
            }
        }

        return null;
    }

    public static void checkValidUser(Integer artistId) {
        Integer authId = SecurityUtils.getStudioLoginUserNo();
        if (authId == null || !Objects.equals(artistId, authId)) {
            throw new InvalidAccessException();
        }
    }

    public static byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        // 1. QR 코드 설정
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 2. 로고 로드
        InputStream logoStream = CommonUtils.class.getClassLoader().getResourceAsStream("Logo.png");
        BufferedImage logoImage = ImageIO.read(Objects.requireNonNull(logoStream));

        // 3. 로고 리사이징 (비율 유지 + 고화질)
        int logoMaxWidth = width / 5;
        int logoMaxHeight = height / 5;
        double scale = Math.min((double) logoMaxWidth / logoImage.getWidth(), (double) logoMaxHeight / logoImage.getHeight());
        int scaledWidth = (int) (logoImage.getWidth() * scale);
        int scaledHeight = (int) (logoImage.getHeight() * scale);

        BufferedImage scaledLogo = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dLogo = scaledLogo.createGraphics();
        g2dLogo.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2dLogo.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2dLogo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dLogo.drawImage(logoImage, 0, 0, scaledWidth, scaledHeight, null);
        g2dLogo.dispose();

        // 4. 로고 삽입
        Graphics2D g2d = qrImage.createGraphics();
        int centerX = (width - scaledWidth) / 2;
        int centerY = (height - scaledHeight) / 2;
        g2d.drawImage(scaledLogo, centerX, centerY, null);
        g2d.dispose();

        // 5. Byte 배열로 변환
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "PNG", outputStream);

        return outputStream.toByteArray();
    }
}
