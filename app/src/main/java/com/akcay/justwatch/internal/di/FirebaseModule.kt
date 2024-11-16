package com.akcay.justwatch.internal.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
  @Provides fun auth(): FirebaseAuth = Firebase.auth

  @Provides fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}
