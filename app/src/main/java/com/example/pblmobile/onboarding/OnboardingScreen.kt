package com.example.pblmobile.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pblmobile.ui.theme.PblMobileTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pages = listOf(OnboardingModel.FirstPage, OnboardingModel.SecondPage, OnboardingModel.ThirdPage)

    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        content = {
            Column(Modifier.padding(it)) {
                HorizontalPager(state = pagerState) { index ->
                    OnboardingContent(pages[index])
                }
            }
        }, bottomBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    if (pagerState.currentPage > 0) {
                        BackButton(Modifier) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    }
                }

                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Indicator(modifier = Modifier, currentPage = pagerState.currentPage, pageSize = pages.size)
                }

                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    NextButton(modifier = Modifier) {
                        if (pagerState.currentPage < pages.size - 1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            coroutineScope.launch {
                                onFinished()
                            }
                        }
                    }
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    PblMobileTheme {
        OnboardingScreen() {}
    }
}