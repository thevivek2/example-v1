package com.thevivek2.example.coroutine

import com.thevivek2.example.common.config.WebSocketConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Aspect
@Component
@Suppress("OPT_IN_USAGE")
open class NotBlockingAdvice {

    private val logger: Log = LogFactory.getLog(WebSocketConfig::class.java)
    /**
     * link: https://docs.spring.io/spring-framework/reference/languages/kotlin/coroutines.html  #Spring AOP
     */
    @Around("@annotation(com.thevivek2.example.coroutine.NotBlockingVoid)")
    fun wrapInCoroutine(joinPoint: ProceedingJoinPoint) {
        wrapInCoroutineInternal(joinPoint);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    open fun wrapInCoroutineInternal(joinPoint: ProceedingJoinPoint){
        logger.debug("executing in coroutine not blocking async")
        GlobalScope.launch {
            joinPoint.proceed()
        }
    }

}