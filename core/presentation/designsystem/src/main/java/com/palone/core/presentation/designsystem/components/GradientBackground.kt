package com.palone.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.palone.core.presentation.designsystem.RuniqueTheme

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    hasToolbar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val smallDimension = minOf(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)
    val smallDimensionPx = with(density) { smallDimension.toPx() }

    val primaryColor = MaterialTheme.colorScheme.primary


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .blur(smallDimension / 3)
                .background(
                    brush = Brush.radialGradient(
                        listOf(
                            primaryColor,
                            MaterialTheme.colorScheme.background
                        ), center = Offset(x = screenWidthPx / 2f, y = -100f),
                        radius = smallDimensionPx / 2f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(if (hasToolbar) Modifier else Modifier.systemBarsPadding())
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun GradientBackgroundPreview() {
    RuniqueTheme {
        GradientBackground(modifier = Modifier.fillMaxSize()) {

        }
    }
}