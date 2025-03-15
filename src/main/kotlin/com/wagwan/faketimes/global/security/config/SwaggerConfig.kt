package com.wagwan.faketimes.global.security.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(configurationInfo())
    }

    private fun configurationInfo(): Info {
        return Info()
            .title("FakeTimes")
            .description("FakeTimes Api DOCS")
            .version("1.0.0")
    }

}