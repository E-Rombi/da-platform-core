package br.com.deliveryapp.daplatformcore.plan.findAll.adapter.`in`.web

import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.`in`.FindAllPlanUseCase
import grpc.br.com.deliveryapp.FindAllPlanRequest
import grpc.br.com.deliveryapp.FindAllPlanResponse
import grpc.br.com.deliveryapp.PlanApiGrpc
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest

@GrpcService
class FindAllPlanGRPC(
    val findAllPlanUseCase: FindAllPlanUseCase
) : PlanApiGrpc.PlanApiImplBase() {

    val defaultPage: Int = 0;
    val defaultItemsPerPage: Int = 10;

    override fun findAllPlan(request: FindAllPlanRequest?, responseObserver: StreamObserver<FindAllPlanResponse>?) {
        val pageable = PageRequest.of(request?.page ?: defaultPage,
            request?.itemsPerPage ?: defaultItemsPerPage)

        val responseBody = findAllPlanUseCase.findAll(pageable)

        responseObserver?.onNext(responseBody)
        responseObserver?.onCompleted()
    }
}