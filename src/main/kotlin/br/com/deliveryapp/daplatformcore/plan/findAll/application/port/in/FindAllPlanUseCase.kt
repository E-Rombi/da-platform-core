package br.com.deliveryapp.daplatformcore.plan.findall.application.port.`in`

import grpc.br.com.deliveryapp.FindAllPlanRequest
import grpc.br.com.deliveryapp.FindAllPlanResponse

interface FindAllPlanUseCase {

    fun findAll(request: FindAllPlanRequest?): FindAllPlanResponse
}