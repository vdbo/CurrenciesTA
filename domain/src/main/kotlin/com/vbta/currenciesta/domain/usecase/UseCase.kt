package com.vbta.currenciesta.domain.usecase

interface UseCase<P, R> {
    fun execute(data: P): R
}