package br.com.deliveryapp.daplatformcore.plan.shared.adapter.out.web

import br.com.deliveryapp.daplatformcore.plan.findAll.model.FindAllPlanDto
import br.com.deliveryapp.daplatformcore.plan.shared.model.exception.FindAllPlanInvalidArgumentException
import com.google.protobuf.Any
import com.google.rpc.BadRequest
import com.google.rpc.Code
import com.google.rpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.StatusProto
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.hibernate.validator.internal.engine.path.PathImpl
import javax.validation.ConstraintViolation

@GrpcAdvice
class GrpcExceptionAdvice {

    @GrpcExceptionHandler
    fun handleInvalidArgument(e: FindAllPlanInvalidArgumentException): StatusRuntimeException {
        val status =  Status.newBuilder()
            .setCode(Code.INVALID_ARGUMENT.number)
            .setMessage("Form with errors")
            .addDetails(
                Any.pack(
                    BadRequest.newBuilder()
                    .addAllFieldViolations(mapErrors(e.errors))
                    .build()
                )
            )
            .build()

        return StatusProto.toStatusRuntimeException(status)
    }

    private fun mapErrors(errors: Iterable<ConstraintViolation<FindAllPlanDto>>)
        : MutableIterable<BadRequest.FieldViolation> {
        return errors.map {
            BadRequest.FieldViolation.newBuilder()
                .setField((it.propertyPath as PathImpl).leafNode.toString())
                .setDescription(it.message)
                .build()
        }.toMutableList()
    }

}