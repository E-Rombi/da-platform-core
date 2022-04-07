package br.com.deliveryapp.daplatformcore.plan.findall.model

import javax.validation.constraints.Positive
import javax.validation.constraints.PositiveOrZero

class FindAllPlanDto(
    @field:PositiveOrZero val page: Int,
    @field:Positive val itemsPerPage: Int
)