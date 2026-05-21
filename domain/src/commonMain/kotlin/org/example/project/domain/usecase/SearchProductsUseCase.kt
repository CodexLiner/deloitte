package org.example.project.domain.usecase

import org.example.project.domain.repository.ProductRepository
import org.example.project.models.Product

class SearchProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(query: String): List<Product> = repository.searchProducts(query)
}
