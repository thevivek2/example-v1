package com.sipios.springsearch.strategies

import com.sipios.springsearch.SearchOperation
import com.thevivek2.example.common.constant.SystemConstant
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import java.time.LocalDateTime
import kotlin.reflect.KClass

class LocalDateTimeStrategy : ParsingStrategy {
    override fun buildPredicate(
        builder: CriteriaBuilder,
        path: Path<*>,
        fieldName: String,
        ops: SearchOperation?,
        value: Any?
    ): Predicate? {
        return when (ops) {
            SearchOperation.GREATER_THAN -> builder.greaterThan(path[fieldName], value as LocalDateTime)
            SearchOperation.LESS_THAN -> builder.lessThan(path[fieldName], value as LocalDateTime)
            SearchOperation.GREATER_THAN_EQUALS -> builder.greaterThanOrEqualTo(path[fieldName], value as LocalDateTime)
            SearchOperation.LESS_THAN_EQUALS -> builder.lessThanOrEqualTo(path[fieldName], value as LocalDateTime)
            else -> super.buildPredicate(builder, path, fieldName, ops, value)
        }
    }

    override fun parse(value: String?, fieldClass: KClass<out Any>): Any? {
        if (value == SearchOperation.NULL) return value
        return LocalDateTime.parse(value, SystemConstant.DATE_TIME_FORMATTER)
    }
}
