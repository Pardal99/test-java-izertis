package com.enterprise.ppardal.infrastructure.adapter.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PricesControllerAdapterTest {

  @Autowired
  private MockMvc mockMvc;

  private static final String ENDPOINT = "/api/v1/prices/find-by-application-date";

  // ========== TEST CASES SUGGESTED FROM REQUIREMENTS ==========

  /*
   * Test case 1
   * Request at 10:00 (GMT+2) on June 14th, 2020 for product 35455 and brand 1
   * Expected price: 35.50
   */
  @Test
  void test1_priceAt10On14th() throws Exception {
    String request = """
        {
          "brand_id": 1,
          "product_id": 35455,
          "application_date": "2020-06-14T10:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(35.50))
        .andExpect(jsonPath("$.brand_id").value(1))
        .andExpect(jsonPath("$.product_id").value(35455));
  }

  /*
   * Test case 2
   * Request at 16:00 (GMT+2) on June 14th, 2020 for product 35455 and brand 1
   * Expected price: 35.50
   */
  @Test
  void test2_priceAt16On14th() throws Exception {
    String request = """
        {
          "brand_id": 1,
          "product_id": 35455,
          "application_date": "2020-06-14T16:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(35.50));
  }

  /*
   * Test case 3
   * Request at 21:00 (GMT+2) on June 14th, 2020 for product 35455 and brand 1
   * Expected price: 35.50
   */
  @Test
  void test3_priceAt21On14th() throws Exception {
    String request = """
        {
          "brand_id": 1,
          "product_id": 35455,
          "application_date": "2020-06-14T21:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(35.50));
  }

  /*
   * Test case 4
   * Request at 10:00 (GMT+2) on June 15th, 2020 for product 35455 and brand 1
   * Expected price: 30.50
   */
  @Test
  void test4_priceAt10On15th() throws Exception {
    String request = """
        {
          "brand_id": 1,
          "product_id": 35455,
          "application_date": "2020-06-15T10:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(30.50));
  }

  /*
   * Test case 5
   * Request at 21:00 (GMT+2) on June 16th, 2020 for product 35455 and brand 1
   * Expected price: 38.95
   */
  @Test
  void test5_priceAt21On16th() throws Exception {
    String request = """
        {
          "brand_id": 1,
          "product_id": 35455,
          "application_date": "2020-06-16T21:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(38.95));
  }

  // ========== ADDITIONAL TEST CASES ==========

  /*
   * Test case 6
   * Request at 18:00 (GMT+2) on June 14th, 2020 for product 35455 and brand 1
   * Expected price: 25.45
   */
  @Test
  void test6_priceAt18On14th() throws Exception {
    String request = """
        {
          "brand_id": 1,
          "product_id": 35455,
          "application_date": "2020-06-14T18:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.price").value(25.45));
  }

  /*
   * Test case 6
   * Request at 18:00 (GMT+2) on June 14th, 2020 for product 35455 and brand 1
   * Expected price: 25.45
   */
  @Test
  void test7_priceNotFound() throws Exception {
    String request = """
        {
          "brand_id": 1,
          "product_id": 1,
          "application_date": "2020-06-14T18:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isNotFound());
  }

  @Test
  void test8_unknownPropertyInRequest() throws Exception {
    String request = """
        {
          "unknown_property": "some_value",
          "brand_id": 1,
          "product_id": 35455,
          "application_date": "2020-06-14T18:00:00+02:00"
        }
        """;

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isBadRequest());
  }

  /**
   * Parameterized test for invalid brand_id values (string, decimal, boolean,
   * object, missing)
   */
  @ParameterizedTest(name = "{index} => {0}")
  @MethodSource("invalidBrandIdRequests")
  void test9_invalidBrandIdCases(String description, String request) throws Exception {

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isBadRequest());
  }

  private static Stream<Arguments> invalidBrandIdRequests() {
    return Stream.of(
        Arguments.of(
            "brand_id as STRING",
            """
                {
                  "brand_id": "1",
                  "product_id": 35455,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "brand_id as DECIMAL",
            """
                {
                  "brand_id": 0.5,
                  "product_id": 35455,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "brand_id as BOOLEAN",
            """
                {
                  "brand_id": true,
                  "product_id": 35455,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "brand_id as OBJECT",
            """
                {
                  "brand_id": {},
                  "product_id": 35455,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "brand_id MISSING",
            """
                {
                  "product_id": 35455,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """));
  }

  /**
   * Parameterized test for invalid product_id values
   */
  @ParameterizedTest(name = "{index} => {0}")
  @MethodSource("invalidProductIdRequests")
  void test10_invalidProductIdCases(String description, String request) throws Exception {

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isBadRequest());
  }

  private static Stream<Arguments> invalidProductIdRequests() {
    return Stream.of(
        Arguments.of(
            "product_id as STRING",
            """
                {
                  "brand_id": 1,
                  "product_id": "35455",
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "product_id as DECIMAL",
            """
                {
                  "brand_id": 1,
                  "product_id": 35455.7,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "product_id as BOOLEAN",
            """
                {
                  "brand_id": 1,
                  "product_id": false,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "product_id as OBJECT",
            """
                {
                  "brand_id": 1,
                  "product_id": {},
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """),
        Arguments.of(
            "product_id MISSING",
            """
                {
                  "brand_id": 1,
                  "application_date": "2025-12-13T11:45:34.388Z"
                }
                """));
  }

  /**
   * Parameterized test for invalid application_date values
   */
  @ParameterizedTest(name = "{index} => {0}")
  @MethodSource("invalidApplicationDateRequests")
  void test11_invalidApplicationDateCases(String description, String request) throws Exception {

    mockMvc.perform(post(ENDPOINT)
        .contentType(MediaType.APPLICATION_JSON)
        .content(request))
        .andExpect(status().isBadRequest());
  }

  private static Stream<Arguments> invalidApplicationDateRequests() {
    return Stream.of(
        Arguments.of(
            "application_date as STRING (non-date text)",
            """
                {
                  "brand_id": 1,
                  "product_id": 35455,
                  "application_date": "not-a-date"
                }
                """),
        Arguments.of(
            "application_date without timezone",
            """
                {
                  "brand_id": 1,
                  "product_id": 35455,
                  "application_date": "2025-12-13T11:45:34"
                }
                """),
        Arguments.of(
            "application_date as NUMBER",
            """
                {
                  "brand_id": 1,
                  "product_id": 35455,
                  "application_date": 1702460734
                }
                """),
        Arguments.of(
            "application_date as OBJECT",
            """
                {
                  "brand_id": 1,
                  "product_id": 35455,
                  "application_date": {}
                }
                """),
        Arguments.of(
            "application_date as ARRAY",
            """
                {
                  "brand_id": 1,
                  "product_id": 35455,
                  "application_date": []
                }
                """),
        Arguments.of(
            "application_date MISSING",
            """
                {
                  "brand_id": 1,
                  "product_id": 35455
                }
                """));
  }

  @Test
  void test12_shouldReturn404WhenEndpointDoesNotExist() throws Exception {
    mockMvc.perform(post("/api/v1/prices/force-error"))
        .andExpect(status().isNotFound());
  }

}