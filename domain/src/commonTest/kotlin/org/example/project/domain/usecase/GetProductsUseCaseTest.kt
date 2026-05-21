package org.example.project.domain.usecase

import kotlinx.coroutines.runBlocking
import org.example.project.domain.repository.ProductRepository
import org.example.project.models.Product
import kotlin.test.Test
import kotlin.test.assertEquals

private class FakeProductRepository(
    private val products: List<Product>
) : ProductRepository {
    override suspend fun getProducts(): List<Product> = products
    override suspend fun searchProducts(query: String): List<Product> = products
    override suspend fun getProductDetail(id: Int): Product = products.first { it.id == id }
}

class GetProductsUseCaseTest {
    @Test
    fun returnsProductsFromRepository() = runBlocking {
        val expected = listOf(
            Product(
                id = 1,
                title = "Test",
                description = "Desc",
                brand = "Brand",
                price = 10.0,
                rating = 4.5,
                thumbnail = "thumb",
                images = emptyList()
            )
        )
        val useCase = GetProductsUseCase(FakeProductRepository(expected))

        val result = useCase()

        assertEquals(expected, result)
    }
}
