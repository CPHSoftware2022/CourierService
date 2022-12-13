package com.example.courier;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;

import io.micrometer.core.instrument.util.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CourierApplication.class)
public class CourierApplicationTests {

    private static final String CREATE_COURIER_REQUEST_PATH = "graphql/request/create-courier.graphql";

    private static final String CREATE_COURIER_RESPONSE_PATH = "graphql/response/create-courier.json.graphql";
    private static final String GRAPHQL_QUERY_REQUEST_PATH = "graphql/request/courier.graphql";
    private static final String GRAPHQL_QUERY_RESPONSE_PATH = "graphql/response/courier.json";

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void returnSpecificCourier() throws IOException, JSONException {

        String findCourier = "courier*";

        GraphQLResponse graphQLResponse = graphQLTestTemplate
                .postForResource(format(GRAPHQL_QUERY_REQUEST_PATH, findCourier));

        String expectedResponseBody = read(format(GRAPHQL_QUERY_RESPONSE_PATH, findCourier));
        System.out.println(expectedResponseBody);

        assertThat(graphQLResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertEquals(expectedResponseBody, graphQLResponse.getRawResponse().getBody(), true);
    }

    private String read(String location) throws IOException {
        return IOUtils
                .toString(new ClassPathResource(location).getInputStream(), StandardCharsets.UTF_8);
    }
}
