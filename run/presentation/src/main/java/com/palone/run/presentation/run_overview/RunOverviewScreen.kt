@file:OptIn(ExperimentalMaterial3Api::class)

package com.palone.run.presentation.run_overview

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.palone.core.presentation.designsystem.AnalyticsIcon
import com.palone.core.presentation.designsystem.LogoIcon
import com.palone.core.presentation.designsystem.LogoutIcon
import com.palone.core.presentation.designsystem.RunIcon
import com.palone.core.presentation.designsystem.RuniqueTheme
import com.palone.core.presentation.designsystem.components.RuniqueFloatingActionButton
import com.palone.core.presentation.designsystem.components.RuniqueScaffold
import com.palone.core.presentation.designsystem.components.RuniqueToolbar
import com.palone.core.presentation.designsystem.components.util.DropDownItem
import com.palone.run.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun RunOverviewScreenRot(
    viewModel: RunOverviewViewModel = koinViewModel(),
    onStartRunClick: () -> Unit,
) {
    RunOverviewScreen(
        onAction = {
            when (it) {
                RunOverviewAction.OnStartClick -> onStartRunClick()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
    RuniqueScaffold(
        withGradient = true,
        topAppBar = {
            RuniqueToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.runique),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropDownItem(
                        AnalyticsIcon,
                        stringResource(id = R.string.analytics)
                    ), DropDownItem(LogoutIcon, stringResource(id = R.string.logout))
                ), onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutClick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                })
        },

        floatingActionButton = {
            RuniqueFloatingActionButton(
                icon = RunIcon,
                onClick = { onAction(RunOverviewAction.OnStartClick) })
        }) {

    }
}

@Preview
@Composable
private fun RunOverviewScreenRotScreenPreview() {
    RuniqueTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}