package com.example.kotlin_2

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kotlin_2.screen.GoalScreen
import com.example.kotlin_2.screen.HistoryScreen
import com.example.kotlin_2.screen.HomeScreen

//import com.example.reply.R

object ReplyRoute {
    const val HOME = "Home"
    const val HISTORY = "History"
    const val Goal = "Goal"
}

data class ReplyTopLevelDestination(
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: Int,
)

class ReplyNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: ReplyTopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

}

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){ HomeScreen() }
        composable("history"){ HistoryScreen() }
        composable("goal"){ GoalScreen() }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<ReplyTopLevelDestination>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (ReplyTopLevelDestination) -> Unit
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.Cyan,
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = item.route == navController.currentDestination?.route,
                onClick = { onItemClick(item) },
                icon = {
                        Icon(painter = painterResource(id = item.selectedIcon),  contentDescription = null)

                }
            )

        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    ReplyTopLevelDestination(
        route = ReplyRoute.HOME,
        selectedIcon = R.drawable.baseline_home_24,
        unselectedIcon = R.drawable.baseline_home_24,
        iconTextId = R.string.home
    ),
    ReplyTopLevelDestination(
        route = ReplyRoute.HISTORY,
        selectedIcon = R.drawable.baseline_history_24,
        unselectedIcon = R.drawable.baseline_history_24,
        iconTextId = R.string.history
    ),
    ReplyTopLevelDestination(
        route = ReplyRoute.Goal,
        selectedIcon = R.drawable.baseline_star_rate_24,
        unselectedIcon = R.drawable.baseline_star_rate_24,
        iconTextId = R.string.goal
    ),
)
