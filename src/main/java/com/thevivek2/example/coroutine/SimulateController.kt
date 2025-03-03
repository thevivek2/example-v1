package com.thevivek2.example.coroutine

import com.thevivek2.example.common.auth.TheVivek2AuthService
import com.thevivek2.example.common.response.ServiceResponse
import kotlinx.coroutines.delay
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/simulate")
open class SimulateController(@Autowired private val usrService: TheVivek2AuthService) {

    /**
     * Perfect Delay Simulate.
     * This API has a fixed delay 50 SEC facilitating a delay in System
     * @return string
     */
    @GetMapping("/delay")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    suspend fun simulateDelay(): ServiceResponse<String> {
        val currentUser = usrService.getCurrentUser();
        delay(50000)
        return ServiceResponse.of("####RES### $currentUser");
    }

    @GetMapping("/unauthorizedAccess")
    @PreAuthorize("hasRole('ROCKET')")
    suspend fun simulateInvalidAccess(): ServiceResponse<String> {
        val currentUser = usrService.getCurrentUser();
        delay(2000)
        return ServiceResponse.of("####RES### $currentUser");
    }

    @GetMapping("/currentUser")
    //Not return ServiceResponse to not make changes in app.js !!
    suspend fun currentUser(): String {
        return usrService.getCurrentUser();
    }

}