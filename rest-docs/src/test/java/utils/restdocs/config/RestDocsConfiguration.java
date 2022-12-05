package utils.restdocs.config;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Snippet;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@TestConfiguration
public class RestDocsConfiguration {
    @Bean
    public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
        return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint());
    }
    public static RestDocumentationResultHandler customDocument(String identifier, Snippet... snippets) {
        return document(identifier
                     , customRequestPreprocessor()
                     , customResponsePreprocessor()
                     , snippets);
    }

    private static OperationRequestPreprocessor customRequestPreprocessor() {
        return preprocessRequest(
            modifyUris()
                    .scheme("http")
                    .host("127.0.0.1")
                    .port(8080)
                    //.removePort() //80 포트 사용 할 경우 표시할 필요가 없으니 해당 메소드 사용해도 무방
                , prettyPrint()
        );
    }
    private static OperationResponsePreprocessor customResponsePreprocessor() {
        return preprocessResponse(prettyPrint());
    }


}
