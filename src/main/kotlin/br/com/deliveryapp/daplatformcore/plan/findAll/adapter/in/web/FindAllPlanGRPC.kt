package br.com.deliveryapp.daplatformcore.plan.findAll.adapter.`in`.web

import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.`in`.FindAllPlanUseCase
import com.google.protobuf.Any
import com.google.rpc.BadRequest
import com.google.rpc.Code
import com.google.rpc.Status
import grpc.br.com.deliveryapp.FindAllPlanRequest
import grpc.br.com.deliveryapp.FindAllPlanResponse
import grpc.br.com.deliveryapp.PlanApiGrpc
import io.grpc.protobuf.StatusProto
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.domain.PageRequest

@GrpcService
class FindAllPlanGRPC(
    val findAllPlanUseCase: FindAllPlanUseCase
) : PlanApiGrpc.PlanApiImplBase() {

    override fun findAllPlan(request: FindAllPlanRequest?, responseObserver: StreamObserver<FindAllPlanResponse>?) {
        val responseBody = findAllPlanUseCase.findAll(request)

        responseObserver?.onNext(responseBody)
        responseObserver?.onCompleted()
    }
}