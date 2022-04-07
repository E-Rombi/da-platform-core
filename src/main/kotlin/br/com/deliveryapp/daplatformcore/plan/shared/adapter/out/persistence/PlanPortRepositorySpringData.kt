package br.com.deliveryapp.daplatformcore.plan.shared.adapter.out.persistence

import br.com.deliveryapp.daplatformcore.plan.findall.application.port.out.FindAllPlanPort
import br.com.deliveryapp.daplatformcore.plan.shared.model.Plan
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PlanPortRepositorySpringData(
  val planRepository: PlanRepository
) : FindAllPlanPort {

    override fun findAll(pageable: Pageable): Page<Plan> {
        return planRepository.findAll(pageable)
    }

}