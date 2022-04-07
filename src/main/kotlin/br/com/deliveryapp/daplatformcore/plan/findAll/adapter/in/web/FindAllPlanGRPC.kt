package br.com.deliveryapp.daplatformcore.plan.findall.adapter.`in`.web

import br.com.deliveryapp.daplatformcore.plan.findall.application.port.`in`.FindAllPlanUseCase
import grpc.br.com.deliveryapp.FindAllPlanRequest
import grpc.br.com.deliveryapp.FindAllPlanResponse
import grpc.br.com.deliveryapp.PlanApiGrpc
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory

@GrpcService
class FindAllPlanGRPC(
    val findAllPlanUseCase: FindAllPlanUseCase
) : PlanApiGrpc.PlanApiImplBase() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun findAllPlan(request: FindAllPlanRequest?, responseObserver: StreamObserver<FindAllPlanResponse>?) {
        val responseBody = findAllPlanUseCase.findAll(request)

        responseObserver?.onNext(responseBody)
        responseObserver?.onCompleted()
        logger.info("action=requestCompleted, cid=${request?.cid}, request=$request")
    }
}