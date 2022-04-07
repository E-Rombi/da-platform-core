package br.com.deliveryapp.daplatformcore.plan.findAll.adapter.`in`.web

import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.`in`.FindAllPlanUseCase
import br.com.deliveryapp.daplatformcore.plan.findAll.application.port.out.FindAllPlanPort
import br.com.deliveryapp.daplatformcore.plan.findAll.application.service.FindAllPlanService
import br.com.deliveryapp.daplatformcore.plan.shared.model.Plan
import grpc.br.com.deliveryapp.FindAllPlanRequest
import grpc.br.com.deliveryapp.FindAllPlanResponse
import io.grpc.internal.testing.StreamRecorder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.math.BigDecimal

internal class FindAllPlanGRPCTest {

    private lateinit var findAllPlanGRPC: FindAllPlanGRPC
    private lateinit var findAllPlanUseCase: FindAllPlanUseCase
    private lateinit var findAllPlanPort: FindAllPlanPort
    private val pageRequest = PageRequest.of(0, 10)

    @BeforeEach
    fun setup() {
        findAllPlanPort = Mockito.mock(FindAllPlanPort::class.java)
        findAllPlanUseCase = FindAllPlanService(findAllPlanPort)
        findAllPlanGRPC = FindAllPlanGRPC(findAllPlanUseCase)
    }

    @Test
    fun `should return two plans`() {
        val request = FindAllPlanRequest.newBuilder()
            .setPage(0)
            .setItemsPerPage(10)
            .build()

        val responseObserver = StreamRecorder.create<FindAllPlanResponse>()

        `when`(findAllPlanPort.findAll(pageRequest))
            .thenReturn(buildPageWithTwoPlans())

        findAllPlanGRPC.findAllPlan(request, responseObserver)

        assertNull(responseObserver.error)

        val response = responseObserver.values

        with(response.first()) {
            assertEquals(2, plansCount)
            assertEquals(1, getPlans(0).id)
            assertEquals(2, getPlans(1).id)
        }
    }

    @Test
    fun `should return zero plans`() {
        val request = FindAllPlanRequest.newBuilder()
            .setPage(0)
            .setItemsPerPage(10)
            .build()

        val responseObserver = StreamRecorder.create<FindAllPlanResponse>()

        `when`(findAllPlanPort.findAll(pageRequest))
            .thenReturn(buildPageWithoutPlans())

        findAllPlanGRPC.findAllPlan(request, responseObserver)

        assertNull(responseObserver.error)

        val response = responseObserver.values

        assertEquals(0, response.first().plansCount)
    }

    private fun buildPageWithTwoPlans(): Page<Plan> {
        return PageImpl(
            listOf(
                Plan("Plan 1", BigDecimal.TEN).apply { id = 1 },
                Plan("Plan 2", BigDecimal.ONE).apply { id = 2 }
            )
        )
    }

    private fun buildPageWithoutPlans(): Page<Plan> = Page.empty()
}