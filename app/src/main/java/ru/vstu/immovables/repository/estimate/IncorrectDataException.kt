package ru.vstu.immovables.repository.estimate

class IncorrectDataException(
        val errors: Map<Long, String>
): Exception()