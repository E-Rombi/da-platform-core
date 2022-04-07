package br.com.deliveryapp.daplatformcore.plan.findall.application.service

import br.com.deliveryapp.daplatformcore.plan.findall.application.port.`in`.FindAllPlanUseCase
import br.com.deliveryapp.daplatformcore.plan.findall.application.port.out.FindAllPlanPort
import br.com.deliveryapp.daplatformcore.plan.findall.model.FindAllPlanDto
import br.com.deliveryapp.daplatformcore.plan.shared.model.Plan
import br.com.deliveryapp.daplatformcore.plan.shared.model.exception.FindAllPlanInvalidArgumentException
import grpc.br.com.deliveryapp.FindAllPlanRequest
import grpc.br.com.deliveryapp.FindAllPlanResponse
import grpc.br.com.deliveryapp.PlanResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.validation.Validator

@Service
class FindAllPlanService(
    val findAllPlanPort: FindAllPlanPort,
    val validator: Validator
) : FindAllPlanUseCase {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun findAll(request: FindAllPlanRequest?): FindAllPlanResponse {
        logger.info("action=toDto, cid=${request?.cid}")
        val dto = request?.toDto() ?: throw RuntimeException("Request is null")

        val violations = validator.validate(dto)
        logger.info("action=validate, violations=$violations, cid=${request.cid}")

        if (violations.isNotEmpty()) throw FindAllPlanInvalidArgumentException(violations)

        val plans = findAllPlanPort.findAll(PageRequest.of(dto.page, dto.itemsPerPage))

        return toResponse(plans, dto)
    }

    private fun toResponse(iterable: Iterable<Plan>, dto: FindAllPlanDto): FindAllPlanResponse {
        return FindAllPlanResponse.newBuilder()
            .setPage(dto.page)
            .setItemsPerPage(dto.itemsPerPage)
            .addAllPlans(
                iterable.map {
                    PlanResponse.newBuilder()
                        .setId(it.id ?: 0)
                        .setName(it.name)
                        .setPrice(it.price.toDouble())
                        .build()
                }
            ).build()
    }

    fun FindAllPlanRequest.toDto(): FindAllPlanDto = FindAllPlanDto(this.page, this.itemsPerPage)
}