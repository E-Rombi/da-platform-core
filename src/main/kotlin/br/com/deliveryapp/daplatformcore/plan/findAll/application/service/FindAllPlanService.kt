package br.com.deliveryapp.daplatformcore.plan.findAll.application.service

import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.`in`.FindAllPlanUseCase
import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.out.FindAllPlanPort
import br.com.deliveryapp.daplatformcore.plan.findAll.model.FindAllPlanDto
import br.com.deliveryapp.daplatformcore.plan.shared.model.Plan
import br.com.deliveryapp.daplatformcore.plan.shared.model.exception.FindAllPlanInvalidArgumentException
import grpc.br.com.deliveryapp.FindAllPlanRequest
import grpc.br.com.deliveryapp.FindAllPlanResponse
import grpc.br.com.deliveryapp.PlanResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import javax.validation.Validator

@Service
class FindAllPlanService(
    val findAllPlanPort: FindAllPlanPort,
    val validator: Validator
) : FindAllPlanUseCase {

    override fun findAll(request: FindAllPlanRequest?): FindAllPlanResponse {
        val dto = request?.toDto() ?: throw RuntimeException("Request is null")
        val violations = validator.validate(dto)

        if (violations.isNotEmpty()) throw FindAllPlanInvalidArgumentException(violations)

        val plans = findAllPlanPort.findAll(PageRequest.of(dto.page, dto.itemsPerPage))

        return toResponse(plans)
    }

    private fun toResponse(iterable: Iterable<Plan>): FindAllPlanResponse {
        return FindAllPlanResponse.newBuilder()
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