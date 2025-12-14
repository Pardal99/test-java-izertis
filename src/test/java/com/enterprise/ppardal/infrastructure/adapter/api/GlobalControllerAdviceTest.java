
package com.enterprise.ppardal.infrastructure.adapter.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.enterprise.ppardal.application.port.PricesServicePort;
import com.enterprise.ppardal.infrastructure.adapter.api.mapper.PricesApiMapper;

@ActiveProfiles("test")
@WebMvcTest(controllers = PricesControllerAdapter.class)
@Import({
    ModelMapper.class,
    PricesApiMapper.class
})
class GlobalControllerAdviceTest {

    private static final String ENDPOINT = "/api/v1/prices/find-by-application-date";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PricesServicePort pricesServicePort;

    @TestConfiguration
    static class TestConfig {

        @Bean
        PricesServicePort pricesServicePort() {
            return Mockito.mock(PricesServicePort.class);
        }
    }

    @Test
    void shouldReturn500WhenUnexpectedRuntimeExceptionOccurs() throws Exception {

        String request = """
                {
                  "brand_id": 1,
                  "product_id": 35455,
                  "application_date": "2020-06-14T10:00:00+02:00"
                }
                """;

        when(pricesServicePort.findProductPriceByApplicationDate(any()))
                .thenThrow(new RuntimeException("Unexpected internal error"));

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isInternalServerError());
    }

}
