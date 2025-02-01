package com.trackmybus.theKeg.core.exceptions

class ResourceNotFoundException(
    message: String,
) : Exception(message)

class ValidationException(
    message: String,
) : Exception(message)
