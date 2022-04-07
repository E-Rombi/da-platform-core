package br.com.deliveryapp.daplatformcore.plan.findAll.application.port.`in`

import grpc.br.com.deliveryapp.FindAllPlanResponse
import org.springframework.data.domain.Pageable

interface FindAllPlanUseCase {

    fun findAll(pageable: Pageable): FindAllPlanResponse
}