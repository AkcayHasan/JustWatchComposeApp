package com.akcay.justwatch.internal.util

import androidx.compose.runtime.Immutable

@Immutable
enum class FieldKey {
    EMAIL,
    PASSWORD,
    CONFIRM_PASSWORD,
    NAME,
    SURNAME,
    PHONE,
    ADDRESS
}

@Immutable
data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

object ValidationUtil {
    
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(false, "Email is required")
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> 
                ValidationResult(false, "Please enter a valid email address")
            else -> ValidationResult(true)
        }
    }
    
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(false, "Password is required")
            password.length < 6 -> ValidationResult(false, "Password must be at least 6 characters")
            else -> ValidationResult(true)
        }
    }
    
    fun validateRequired(value: String, fieldName: String): ValidationResult {
        return when {
            value.isBlank() -> ValidationResult(false, "$fieldName is required")
            else -> ValidationResult(true)
        }
    }
    
    fun validateMinLength(value: String, minLength: Int, fieldName: String): ValidationResult {
        return when {
            value.isBlank() -> ValidationResult(false, "$fieldName is required")
            value.length < minLength -> ValidationResult(false, "$fieldName must be at least $minLength characters")
            else -> ValidationResult(true)
        }
    }
    
    fun validateName(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult(false, "Name is required")
            name.length < 2 -> ValidationResult(false, "Name must be at least 2 characters")
            !name.matches(Regex("^[a-zA-Z\\s]+$")) -> ValidationResult(false, "Name can only contain letters and spaces")
            else -> ValidationResult(true)
        }
    }
    
    fun validateSurname(surname: String): ValidationResult {
        return when {
            surname.isBlank() -> ValidationResult(false, "Surname is required")
            surname.length < 2 -> ValidationResult(false, "Surname must be at least 2 characters")
            !surname.matches(Regex("^[a-zA-Z\\s]+$")) -> ValidationResult(false, "Surname can only contain letters and spaces")
            else -> ValidationResult(true)
        }
    }
}

@Immutable
data class FieldValidationState(
    val hasError: Boolean = false,
    val errorMessage: String? = null
)

@Immutable
data class FormValidationState(
    val fields: Map<FieldKey, FieldValidationState> = emptyMap(),
    val showDialog: Boolean = false,
    val dialogMessage: String? = null
)
