package com.vinh.vinflow.domain.usecase.category

import com.vinh.vinflow.domain.model.Category
import com.vinh.vinflow.domain.model.TransactionType
import com.vinh.vinflow.domain.repository.CategoryRepository
import javax.inject.Inject

class SeedDefaultCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): SeedDefaultCategoriesResult {
        if (repository.getCategoryCount() > 0) {
            return SeedDefaultCategoriesResult.Skipped
        }

        val now = System.currentTimeMillis()
        repository.insertCategories(defaultCategories(now))
        return SeedDefaultCategoriesResult.Seeded
    }

    private fun defaultCategories(now: Long): List<Category> {
        return listOf(
            Category(
                name = "Food",
                type = TransactionType.EXPENSE,
                iconName = "restaurant",
                colorHex = "#f68d1f",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Transportation",
                type = TransactionType.EXPENSE,
                iconName = "directions_bus",
                colorHex = "#7a8aba",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Shopping",
                type = TransactionType.EXPENSE,
                iconName = "shopping_bag",
                colorHex = "#ecab37",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Entertainment",
                type = TransactionType.EXPENSE,
                iconName = "sports_esports",
                colorHex = "#acace7",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Health",
                type = TransactionType.EXPENSE,
                iconName = "medical_services",
                colorHex = "#e60012",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Education",
                type = TransactionType.EXPENSE,
                iconName = "school",
                colorHex = "#9fbee7",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Other",
                type = TransactionType.EXPENSE,
                iconName = "category",
                colorHex = "#3d4f97",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Salary",
                type = TransactionType.INCOME,
                iconName = "payments",
                colorHex = "#e48600",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Bonus",
                type = TransactionType.INCOME,
                iconName = "redeem",
                colorHex = "#ecab37",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Investment",
                type = TransactionType.INCOME,
                iconName = "trending_up",
                colorHex = "#7a8aba",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Gift",
                type = TransactionType.INCOME,
                iconName = "card_giftcard",
                colorHex = "#acace7",
                createdAt = now,
                updatedAt = now
            ),
            Category(
                name = "Other",
                type = TransactionType.INCOME,
                iconName = "category",
                colorHex = "#3d4f97",
                createdAt = now,
                updatedAt = now
            )
        )
    }
}

sealed interface SeedDefaultCategoriesResult {
    data object Seeded : SeedDefaultCategoriesResult
    data object Skipped : SeedDefaultCategoriesResult
}
