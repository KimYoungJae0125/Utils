package utils.restdocs.integration;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static utils.restdocs.config.RestDocsConfiguration.customDocument;

@WebMvcTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class RestDocsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private String endPoint = "/rest-docs";

    private String GET_API_IDENTIFIER = "rest-docs/get";
    private String POST_API_IDENTIFIER = "rest-docs/post";
    private String PUT_API_IDENTIFIER = "rest-docs/put";
    private String PATCH_API_IDENTIFIER = "rest-docs/patch";
    private String DELETE_API_IDENTIFIER = "rest-docs/delete";

    private JsonMapper jsonMapper;

    @BeforeEach
    void setUp() {jsonMapper = JsonMapper.builder().build();}

    @Test
    @DisplayName("Get API 문서 생성")
    void createRestDocsByGet() throws Exception {
        mockMvc.perform(get(endPoint + "/sample")
                        .header("Authorization", "header Sample"))
                .andDo(customDocument(GET_API_IDENTIFIER
                        , requestHeaders(headerWithName("Authorization").description("인증 관련 정보"))
                        , responseFields(sampleFields())
                      )
                );
    }

    @Test
    @DisplayName("Post API 문서 생성")
    void createRestDocsByPost() throws Exception {
        var body = sampleData();

        mockMvc.perform(post(endPoint)
                        .header("Authorization", "header Sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(body)))
                .andDo(customDocument(POST_API_IDENTIFIER
                        , requestHeaders(headerWithName("Authorization").description("인증 관련 정보"))
                        , requestFields(sampleFields())
                        , responseFields(sampleFields())
                      )
                );
    }

    @Test
    @DisplayName("Put API 문서 생성")
    void createRestDocsByPut() throws Exception {
        var body = sampleData();

        mockMvc.perform(put(endPoint)
                        .header("Authorization", "header Sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(body)))
                .andDo(customDocument(PUT_API_IDENTIFIER
                        , requestHeaders(headerWithName("Authorization").description("인증 관련 정보"))
                        , requestFields(sampleFields())
                        , responseFields(sampleFields())
                      )
                );
    }

    @Test
    @DisplayName("Patch API 문서 생성")
    void createRestDocsByPatch() throws Exception {
        var body = sampleData();

        mockMvc.perform(patch(endPoint)
                        .header("Authorization", "header Sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(body)))
                .andDo(customDocument(PATCH_API_IDENTIFIER
                        , requestHeaders(headerWithName("Authorization").description("인증 관련 정보"))
                        , requestFields(sampleFields())
                        , responseFields(sampleFields())
                      )
                );
    }

    @Test
    @DisplayName("Delete API 문서 생성")
    void createRestDocsByDelete() throws Exception {
        var body = sampleData();

        mockMvc.perform(delete(endPoint)
                        .header("Authorization", "header Sample")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(body)))
                .andDo(customDocument(DELETE_API_IDENTIFIER
                                , requestHeaders(headerWithName("Authorization").description("인증 관련 정보"))
                                , requestFields(sampleFields())
                                , responseFields(sampleFields())
                        )
                );
    }

    private FieldDescriptor[] sampleFields() {
        return new FieldDescriptor[]{
                  fieldWithPath("String sample").type(JsonFieldType.STRING).description("String일 경우")
                , fieldWithPath("Number sample").type(JsonFieldType.NUMBER).description("Number일 경우")
                , fieldWithPath("Array sample").type(JsonFieldType.ARRAY).description("Array일 경우")
                , fieldWithPath("Boolean sample").type(JsonFieldType.BOOLEAN).description("Boolean일 경우")
                , fieldWithPath("Object sample").type(JsonFieldType.OBJECT).description("Object일 경우")
                , fieldWithPath("Null sample").type(JsonFieldType.NULL).description("Null일 경우")
        };
    }

    private Map<String, Object> sampleData() {
        return new HashMap<>(){{
                    put("String sample", "hello");
                    put("Number sample", 1);
                    put("Array sample", new ArrayList<>());
                    put("Boolean sample", true);
                    put("Object sample", new HashMap<>());
                    put("Null sample", null);
                }};
    }

}
