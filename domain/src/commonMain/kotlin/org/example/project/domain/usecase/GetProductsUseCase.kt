package org.example.project.domain.usecase

import org.example.project.domain.repository.ProductRepository
import org.example.project.models.Product

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> = repository.getProducts()
}
