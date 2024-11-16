package com.akcay.justwatch.data.remote.model.response.movie.moviemodel

data class AuthUser(
  val name: String? = "",
  val email: String? = "",
  val id: String? = "",
  val isAnonymous: Boolean? = true
)

data class User(
  val firstName: String? = "",
  val lastName: String? = "",
)
