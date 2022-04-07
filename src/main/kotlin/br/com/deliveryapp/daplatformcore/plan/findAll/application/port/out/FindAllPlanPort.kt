package br.com.deliveryapp.daplatformcore.plan.findall.application.port.out

import br.com.deliveryapp.daplatformcore.plan.shared.model.Plan
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FindAllPlanPort {

    fun findAll(pageable: Pageable): Page<Plan>
}