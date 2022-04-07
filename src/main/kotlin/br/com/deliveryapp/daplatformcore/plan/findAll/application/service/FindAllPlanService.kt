package br.com.deliveryapp.daplatformcore.plan.findAll.application.service

import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.`in`.FindAllPlanUseCase
import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.out.FindAllPlanPort
import br.com.deliveryapp.daplatformcore.plan.shared.model.Plan
import grpc.br.com.deliveryapp.FindAllPlanResponse
import grpc.br.com.deliveryapp.PlanResponse
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class FindAllPlanService(
    val findAllPlanPort: FindAllPlanPort
) : FindAllPlanUseCase {

    override fun findAll(pageable: Pageable): FindAllPlanResponse {
        val plans = findAllPlanPort.findAll(pageable)

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
}