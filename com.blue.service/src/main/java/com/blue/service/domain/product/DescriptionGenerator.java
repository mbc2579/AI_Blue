package com.blue.service.domain.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class DescriptionGenerator {
    private final String apiKey = "실제 API 키로 대체"; // 실제 API 키로 대체
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

    public String generateDescription(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 본문 구성
        String requestBody = String.format(
                "{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}",
                prompt
        );

        // HttpHeaders를 사용하여 요청의 콘텐츠 유형을 application/json으로 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HttpEntity를 사용하여 요청 본문과 헤더를 포함한 요청 엔티티를 생성
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);


        // RestTemplate을 사용하여 POST 요청을 API에 보냄 responseEntity.getBody()를 통해 응답
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            String responseBody = responseEntity.getBody();

            // 로그에 응답 출력
            System.out.println("AI API Response: " + responseBody);

            return parseDescription(responseBody);
        } catch (HttpClientErrorException e) {
            System.err.println("클라이언트에 에러가 발생하였습니다.: " + e.getMessage());
            return "잘못된 요청입니다.";
        } catch (HttpServerErrorException e) {
            System.err.println("서버 오류가 발생했습니다: " + e.getMessage());
            return "서비스를 사용할 수 없습니다: 나중에 다시 시도하십시오.";
        } catch (Exception e) {
            System.err.println("예기치 않은 오류가 발생했습니다: " + e.getMessage());
            return "Description generation failed";
        }
    }

    private String parseDescription(String responseBody) {
        try {
            // ObjectMapper를 사용하여 JSON 문자열을 JsonNode로 변환
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode candidatesNode = rootNode.path("candidates");


            // 노드 탐색
            // {
            //    "contents":[
            //        {
            //            "parts":[
            //                {
            //                    "text":"햄버거 메뉴 설명해줘 50자 이내로"
            //                }
            //            ]
            //        }
            //    ]
            //}
            // 요청을 다음과 같이 진행하기 때문에 content -> parts -> test 순으로 탐색
            if (candidatesNode.isArray() && candidatesNode.size() > 0) {
                JsonNode contentNode = candidatesNode.get(0).path("content");
                JsonNode partsNode = contentNode.path("parts");

                if (partsNode.isArray() && partsNode.size() > 0) {
                    return partsNode.get(0).path("text").asText();
                }
            }
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
        return "Description generation failed";
    }
}
