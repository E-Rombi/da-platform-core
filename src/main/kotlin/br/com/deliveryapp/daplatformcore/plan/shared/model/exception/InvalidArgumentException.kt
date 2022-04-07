package br.com.deliveryapp.daplatformcore.plan.shared.model.exception

import br.com.deliveryapp.daplatformcore.plan.findAll.model.FindAllPlanDto
import javax.validation.ConstraintViolation

class FindAllPlanInvalidArgumentException(val errors: Iterable<ConstraintViolation<FindAllPlanDto>>) : RuntimeException()