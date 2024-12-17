package com.akcay.justwatch.internal.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class SubComposeID {
  PRE_CALCULATE_ITEM,
  ITEM,
  INDICATOR
}

data class TabPosition(
  val left: Dp, val width: Dp
)

@Composable
fun JWTabRow(
  containerColor: Color = Color.Blue,
  indicatorColor: Color = MaterialTheme.colorScheme.primary,
  containerShape: Shape = CircleShape,
  indicatorShape: Shape = CircleShape,
  paddingValues: PaddingValues = PaddingValues(4.dp),
  animationSpec: AnimationSpec<Dp> = tween(durationMillis = 250, easing = FastOutSlowInEasing),
  fixedSize: Boolean = true,
  selectedTabPosition: Int = 0,
  tabItem: @Composable () -> Unit
) {
  Surface(
    color = containerColor,
    shape = containerShape
  ) {
    SubcomposeLayout(
      modifier = Modifier
        .padding(paddingValues)
        .selectableGroup()
    ) { constraints ->
      val tabMeasurable = subcompose(SubComposeID.PRE_CALCULATE_ITEM, tabItem)
        .map { it.measure(constraints) }

      val itemsCount = tabMeasurable.size
      val maxItemWidth = tabMeasurable.maxOf { it.width }
      val maxItemHeight = tabMeasurable.maxOf { it.height }

      val tabPlaceable = subcompose(SubComposeID.ITEM, tabItem).map {
        val c = if (fixedSize) constraints.copy(
          minWidth = maxItemWidth,
          maxWidth = maxItemWidth,
          minHeight = maxItemHeight
        ) else constraints
        it.measure(c)
      }

      val tabPositions = tabPlaceable.mapIndexed { index, placeable ->
        val itemWidth = if (fixedSize) maxItemWidth else placeable.width
        val left = if (fixedSize) {
          maxItemWidth * index
        } else {
          tabPlaceable.take(index).sumOf { it.width }
        }
        TabPosition(left.toDp(), itemWidth.toDp())
      }

      val tabRowWidth =
        if (fixedSize) maxItemWidth * itemsCount else tabPlaceable.sumOf { it.width }

      layout(width = tabRowWidth, height = maxItemHeight) {
        subcompose(SubComposeID.INDICATOR) {
          Box(
            modifier = Modifier
              .tabIndicator(tabPosition = tabPositions[selectedTabPosition], animationSpec)
              .fillMaxWidth()
              .height(maxItemHeight.toDp())
              .background(color = indicatorColor, shape = indicatorShape)
          )
        }.firstOrNull()?.measure(Constraints.fixed(width = tabRowWidth, height = maxItemHeight))
          ?.placeRelative(x = 0, y = 0)

        tabPlaceable.forEachIndexed { index, placeable ->
          val x = if (fixedSize) {
            maxItemWidth * index
          } else {
            tabPlaceable.take(index).sumOf { it.width }
          }
          placeable.placeRelative(x = x, y = 0)
        }
      }
    }
  }
}

fun Modifier.tabIndicator(
  tabPosition: TabPosition,
  animationSpec: AnimationSpec<Dp>
): Modifier = composed(
  inspectorInfo = debugInspectorInfo {
    name = "tabIndicatorOffset"
    value = tabPosition
  }
) {

  val currentTabWidth by animateDpAsState(
    targetValue = tabPosition.width,
    animationSpec = animationSpec,
    label = ""
  )

  val indicatorOffset by animateDpAsState(
    targetValue = tabPosition.left,
    animationSpec = animationSpec,
    label = ""
  )


  fillMaxWidth()
    .wrapContentSize(align = Alignment.BottomStart)
    .offset(x = indicatorOffset)
    .width(currentTabWidth)
    .fillMaxHeight()
}

@Composable
fun JWTabTitle(
  modifier: Modifier = Modifier,
  title: String,
  position: Int,
  onClick: (Int) -> Unit
) {

  Text(
    text = title,
    modifier = modifier
      .wrapContentWidth(align = Alignment.CenterHorizontally)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
      ) { onClick(position) },
    color = MaterialTheme.colorScheme.onPrimary
  )
}

@Preview
@Composable
fun JWTabPreview() {
  val tabItems = listOf("popular", "upcoming")
  Surface(modifier = Modifier
    .fillMaxWidth()
    .height(200.dp)) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .background(Color.White)
    ) {
      JWTabRow(
        indicatorColor = Color.Red
      ) {
        tabItems.forEachIndexed { index, name ->
          JWTabTitle(
            modifier = Modifier
              .padding(horizontal = 40.dp, vertical = 10.dp),
            title = name,
            position = index
          ) {}
        }
      }
    }
  }
}