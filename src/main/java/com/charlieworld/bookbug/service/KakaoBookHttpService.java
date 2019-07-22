package com.charlieworld.bookbug.service;

import com.charlieworld.bookbug.entity.TargetType;
import com.charlieworld.bookbug.exception.CustomException;
import com.charlieworld.bookbug.http.model.kakao.KakaoBookModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoBookHttpService {

    @Value("${spring.kakao.rest-api-key}")
    private String kakaoAppKey;

    @Value("${spring.kakao.host}")
    private String host;

    private RestTemplate restTemplate = new RestTemplate();

    private HttpEntity makeAuthHeader() {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoAppKey);
        return new HttpEntity(header);
    }

    public KakaoBookModel getBooks(int page, String query, TargetType targetType) throws CustomException {
        KakaoBookModel model = null;
        try {
            String apiPath = "/v3/search/book";
            String params = String.format("?target=%s&page=%d&query=%s", targetType.getValue(), page, query);
            String uri = host + apiPath + params;
            ResponseEntity<KakaoBookModel> response = restTemplate
                    .exchange(uri, HttpMethod.GET, makeAuthHeader(), KakaoBookModel.class);
            model = response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "서버와 통신 중 문제가 발생 하였습니다.");
        }
        return model;
    }
}
