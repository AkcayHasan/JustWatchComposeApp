package com.akcay.justwatch.internal.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry

fun defaultEnterTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) {
  return {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(200))
  }
}

fun defaultExitTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) {
  return {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(200))
  }
}

fun defaultPopEnterTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) {
  return {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
  }
}

fun defaultPopExitTransition(): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) {
  return {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(200))
  }
}