package br.com.deliveryapp.daplatformcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DaPlatformCoreApplication

fun main(args: Array<String>) {
    runApplication<DaPlatformCoreApplication>(*args)
}