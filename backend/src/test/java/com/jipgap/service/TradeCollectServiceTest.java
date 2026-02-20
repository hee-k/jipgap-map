package com.jipgap.service;

import com.jipgap.dto.CollectResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// removed unused import
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TradeCollectServiceTest {

    @Autowired
    private TradeCollectService tradeCollectService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private org.springframework.web.client.RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS sgg_boundary (sgg_cd VARCHAR(5));");
        jdbcTemplate.execute("DELETE FROM sgg_boundary;");
        jdbcTemplate.execute("INSERT INTO sgg_boundary (sgg_cd) VALUES ('11110');");
    }

    @Test
    void collect_success_parses_and_inserts() {
        mockServer.expect(MockRestRequestMatchers.requestTo(org.hamcrest.Matchers.containsString("LAWD_CD=11110")))
                .andRespond(MockRestResponseCreators.withSuccess(successXml(), org.springframework.http.MediaType.APPLICATION_XML));

        CollectResponse response = tradeCollectService.collect(2025, 1);

        assertThat(response.getTotalRequested()).isEqualTo(1);
        assertThat(response.getTotalInserted()).isEqualTo(1);
        assertThat(response.getFailedSggCd()).isEmpty();
    }

    @Test
    void collect_no_data_returns_zero() {
        mockServer.expect(MockRestRequestMatchers.requestTo(org.hamcrest.Matchers.containsString("LAWD_CD=11110")))
                .andRespond(MockRestResponseCreators.withSuccess(noDataXml(), org.springframework.http.MediaType.APPLICATION_XML));

        CollectResponse response = tradeCollectService.collect(2025, 1);

        assertThat(response.getTotalInserted()).isEqualTo(0);
    }

    private String successXml() {
        return """
                <response>
                    <header>
                        <resultCode>00</resultCode>
                        <resultMsg>OK</resultMsg>
                    </header>
                    <body>
                        <items>
                            <item>
                                <dealAmount>12,000</dealAmount>
                                <dealYear>2025</dealYear>
                                <dealMonth>1</dealMonth>
                                <dealDay>10</dealDay>
                                <dong>신사동</dong>
                                <aptName>테스트아파트</aptName>
                                <excluUseAr>84.97</excluUseAr>
                                <floor>10</floor>
                                <buildYear>2010</buildYear>
                                <cdealType></cdealType>
                                <cdealDay></cdealDay>
                            </item>
                        </items>
                    </body>
                </response>
                """;
    }

    private String noDataXml() {
        return """
                <response>
                    <header>
                        <resultCode>03</resultCode>
                        <resultMsg>NO DATA</resultMsg>
                    </header>
                </response>
                """;
    }
}
