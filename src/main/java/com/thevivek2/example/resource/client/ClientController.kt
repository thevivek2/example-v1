package com.thevivek2.example.resource.client

import com.sipios.springsearch.anotation.SearchSpec
import com.thevivek2.example.common.anotation.Strict
import com.thevivek2.example.common.response.ServiceResponse
import com.thevivek2.example.resource.client.ClientAPIs.CLIENT_API
import com.thevivek2.example.resource.client.ClientAPIs.CLIENT_API_STRICT_EXAMPLE
import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientController(
    private val clientQueryExecutor: JpaSpecificationExecutor<Client>,
    private val clientRepo: JpaRepository<Client, Long>
) {

    @GetMapping(CLIENT_API)
    fun clientDealerMaster(
        @Parameter(
            description = "Search query in the form of a string used to filter the results. "
                    + "The query is composed of key-value pairs with logical operators, such as `AND`, `OR`, `IN`, and comparison operators like `=`, `>`, `<`, etc.\n\n"
                    + "### Supported Operators:\n"
                    + "| Operator | Description                   | Example                      |\n"
                    + "|----------|-------------------------------|------------------------------|\n"
                    + "| `:`      | Equal                         | `clientOrDealer:C`           |\n"
                    + "| `!`      | Not equal                     | `clientOrDealer!C`           |\n"
                    + "| `>`      | Greater than                  | `createdAt>2020-10-10 10:10:10` |\n"
                    + "| `>:`     | Greater than or equal         | `createdAt>:2020-10-10 10:10:10` |\n"
                    + "| `<`      | Less than                     | `onlineOrOffline<2`          |\n"
                    + "| `<:`     | Less than or equal            | `onlineOrOffline<:2`         |\n"
                    + "| `*`      | Starts with                   | `displayName:*thevivek2`     |\n"
                    + "| `*`      | Ends with                     | `displayName:Vivek Adh*`     |\n"
                    + "| `*`      | Contains                      | `displayName:*thevivek2*`    |\n"
                    + "| `OR`     | Logical OR                     | `clientOrDealer:C OR clientOrDealer:B` |\n"
                    + "| `AND`    | Logical AND                    | `displayName:Aston* AND onlineOrOffline<2` |\n"
                    + "| `IN`     | Value is in list               | `clientOrDealer IN ['C', 'B']` |\n"
                    + "| `NOT IN` | Value is not in list           | `clientOrDealer NOT IN ['C', 'B']` |\n"
                    + "| `IS EMPTY` | Collection field is empty   | `bikes IS EMPTY`              |\n"
                    + "| `IS NOT EMPTY` | Collection is not empty | `bikes IS NOT EMPTY`          |\n"
                    + "| `IS NULL` | Field is null                 | `displayName IS NULL`        |\n"
                    + "| `IS NOT NULL` | Field is not null         | `displayName IS NOT NULL`    |\n"
                    + "| `()`     | Grouping with parentheses      | `displayName:Nissan OR (displayName:Chevrolet AND clientOrDealer:C)` |\n"
                    + "| `BETWEEN` | Value between two values      | `createdAt 2020-10-10 10:10:10 AND 2025-10-10 10:10:10` |\n"
                    + "| `NOT BETWEEN` | Value not between         | `createdAt NOT BETWEEN 2020-10-10 10:10:10 AND 2025-10-10 10:10:10` |\n\n"
                    + "### Combining Multiple Conditions:\n"
                    + "- **Using `AND`**: `{\"search\":\"displayName:JIWAN2*\"}`\n"
                    + "- **Using `AND`**: `{\"search\"=\"displayName:JIWAN* AND onlineOrOffline<2 AND clientOrDealer:C\"}`\n"
                    + "- **Using `OR`**: `{\"search\"=\"clientOrDealer:C OR clientOrDealer:B\"}`\n"
                    + "- **Using Parentheses for grouping**: `{\"search\"=\"displayName:JIWAN KATUWAL OR (displayName:Chevrolet AND clientOrDealer:C)\"}`\n"
                    + "\nThe `spec` parameter here is a **Specification** object, which accepts a string formatted query for filtering.\n"
                    + "The string query can be complex and may involve multiple search conditions joined by logical operators."
        )
        @SearchSpec spec: Specification<Client>?,
        @ParameterObject
        @Parameter(
            description = "Pagination and sorting parameters:\n"
                    + "- `page`: Zero-based page index (default: 0)\n"
                    + "- `size`: Number of records per page (default: 20)\n"
                    + "- `sort`: Sorting criteria in the format `field,asc|desc` (e.g., `name,asc`).\n"
                    + "  - Multiple sorting parameters can be provided, e.g., `sort=name,asc&sort=createdAt,desc`."
        ) pageable: Pageable
    ): ServiceResponse<Page<Client>> {
        return ServiceResponse.of(clientQueryExecutor.findAll(spec, pageable))
    }

    @GetMapping(CLIENT_API_STRICT_EXAMPLE)
    fun example(
        @Strict @SearchSpec spec: Specification<Client>?,
        @ParameterObject
        @Parameter(
            description = "Pagination and sorting parameters:\n"
                    + "- `page`: Zero-based page index (default: 0)\n"
                    + "- `size`: Number of records per page (default: 20)\n"
                    + "- `sort`: Sorting criteria in the format `field,asc|desc` (e.g., `name,asc`).\n"
                    + "  - Multiple sorting parameters can be provided, e.g., `sort=name,asc&sort=createdAt,desc`."
        ) pageable: Pageable
    ): ServiceResponse<Page<Client>> {
        return ServiceResponse.of(clientQueryExecutor.findAll(spec, pageable))
    }

    @PostMapping(CLIENT_API)
    fun createOrUpdate(@RequestBody client: Client): ServiceResponse<Client> {
        return ServiceResponse.of(clientRepo.save(client))
    }

}