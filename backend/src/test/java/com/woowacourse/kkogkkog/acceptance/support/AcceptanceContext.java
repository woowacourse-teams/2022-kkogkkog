package com.woowacourse.kkogkkog.acceptance.support;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;
import org.springframework.http.MediaType;

public class AcceptanceContext {

    public static ExtractableResponse<Response> invokeGet(String path, Object... params) {
        return RestAssured.given().log().all()
            .when()
            .get(path, params)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokeGetWithQueryParams(String path,
                                                                         Map<String, Object> params) {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .queryParams(params)
            .get(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokePost(String path, Object data) {
        return RestAssured.given().log().all()
            .body(data)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokePut(String path, Object data) {
        return RestAssured.given().log().all()
            .body(data)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokeDelete(String path, Object data) {
        return RestAssured.given().log().all()
            .body(data)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokeGetWithToken(String path, String token,
                                                                   Object... params) {
        return RestAssured.given().log().all()
            .auth().oauth2(token)
            .when()
            .get(path, params)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokeGetWithTokenAndQueryParams(String path,
                                                                                 String token,
                                                                                 Map<String, Object> params) {
        return RestAssured.given().log().all()
            .auth().oauth2(token)
            .queryParams(params)
            .when()
            .get(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokePostWithToken(String path, String token,
                                                                    Object data) {
        return RestAssured.given().log().all()
            .auth().oauth2(token)
            .body(data)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokePutWithToken(String path, String token,
                                                                   Object data) {
        return RestAssured.given().log().all()
            .auth().oauth2(token)
            .body(data)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokePutWithToken(String path, String token) {
        return RestAssured.given().log().all()
            .auth().oauth2(token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .put(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokePatchWithToken(String path, String token) {
        return RestAssured.given().log().all()
            .auth().oauth2(token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .patch(path)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> invokeDeleteWithToken(String path, String token,
                                                                      Object data) {
        return RestAssured.given().log().all()
            .auth().oauth2(token)
            .body(data)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .delete(path)
            .then().log().all()
            .extract();
    }
}
