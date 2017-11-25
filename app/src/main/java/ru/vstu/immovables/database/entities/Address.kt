package ru.vstu.immovables.database.entities

data class Address(
        private val country: String,
        private val street: String,
        private val city: String,
        private val postalNumber: Int
)