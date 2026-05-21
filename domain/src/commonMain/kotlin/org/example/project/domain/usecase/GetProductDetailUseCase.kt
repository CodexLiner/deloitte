package org.example.project.domain.usecase

import org.example.project.domain.repository.ProductRepository
import org.example.project.models.Product

class GetProductDetailUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product = repository.getProductDetail(id)
}
