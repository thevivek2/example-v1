package com.thevivek2.example.common.example.exchange

import com.thevivek2.example.common.client.HttpResponse
import com.thevivek2.example.common.client.TheVivek2WebClient
import com.thevivek2.example.common.config.TheVivek2TestConfig
import com.thevivek2.example.common.response.ServiceResponse
import kotlinx.coroutines.coroutineScope
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/web-client")
class TheVivek2BlockingController(
    final val testConfig: TheVivek2TestConfig,
    final val restClient: TheVivek2WebClient
) {

    private val logger: Log = LogFactory.getLog(TheVivek2BlockingController::class.java)

    @GetMapping("/then-this")
    suspend fun get(): ServiceResponse<HttpResponse<String>> = coroutineScope {
        logger.trace("inside coroutineScope and waiting until completion")
        ServiceResponse.of(restClient.get(testConfig.getExternalServiceUrl()))
    }

    @GetMapping
    suspend fun doGet(): ServiceResponse<HttpResponse<String>> {
        return ServiceResponse.of(restClient.get(testConfig.getExternalServiceUrl()))
    }
}