package org.example.project.screens

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
    data class Navigation(val destination: NavigationDestination) : UiState<Nothing>()
}

sealed class NavigationDestination {
    data class ProductDetail(val productId: Int) : NavigationDestination()
}
