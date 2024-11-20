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

fun defaultEnterTransition(durationTime: Int = 400): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) {
  return {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(durationTime))
  }
}

fun defaultExitTransition(durationTime: Int = 400): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) {
  return {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(durationTime))
  }
}

fun defaultPopEnterTransition(durationTime: Int = 400): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?) {
  return {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(durationTime))
  }
}

fun defaultPopExitTransition(durationTime: Int = 400): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?) {
  return {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(durationTime))
  }
}