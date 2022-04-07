package br.com.deliveryapp.daplatformcore.plan.shared.adapter.out.persistence

import br.com.deliveryapp.daplatformcore.plan.shared.model.Plan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlanRepository : JpaRepository<Plan, Long>