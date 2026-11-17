
package com.dashforge.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BG = Color(0xFF080D17)
private val PANEL = Color(0xFF141E30)
private val BLUE = Color(0xFF398BFF)
private val MUTED = Color(0xFF8999B0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { DashForgeApp() }
    }
}

enum class Page { HOME, MODS, LIBRARY, SETTINGS }

@Composable
fun DashForgeApp() {
    var page by remember { mutableStateOf(Page.HOME) }

    LaunchedEffect(Unit) {
        ModuleCatalog.registerDefaults()
    }

    MaterialTheme(colorScheme = darkColorScheme(primary = BLUE)) {
        Scaffold(
            containerColor = BG,
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF101827)) {
                    NavItem("Home", Icons.Outlined.Home, page == Page.HOME) {
                        page = Page.HOME
                    }
                    NavItem("Mods", Icons.Outlined.Tune, page == Page.MODS) {
                        page = Page.MODS
                    }
                    NavItem("Library", Icons.Outlined.Apps, page == Page.LIBRARY) {
                        page = Page.LIBRARY
                    }
                    NavItem("Settings", Icons.Outlined.Settings, page == Page.SETTINGS) {
                        page = Page.SETTINGS
                    }
                }
            }
        ) { padding ->
            Box(Modifier.fillMaxSize().padding(padding)) {
                when (page) {
                    Page.HOME -> HomeScreen()
                    Page.MODS -> ModsScreen()
                    Page.LIBRARY -> LibraryScreen()
                    Page.SETTINGS -> SettingsScreen()
                }
            }
        }
    }
}

@Composable
fun RowScope.NavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label, fontSize = 11.sp) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = BLUE,
            selectedTextColor = BLUE,
            unselectedIconColor = MUTED,
            unselectedTextColor = MUTED,
            indicatorColor = Color(0xFF1E304C)
        )
    )
}

@Composable
fun Heading(title: String, subtitle: String) {
    Column {
        Text(title, fontSize = 27.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, fontSize = 13.sp, color = MUTED)
    }
}

@Composable
fun HeroCard() {
    Column(
        Modifier.fillMaxWidth()
            .background(Color(0xFF172D4C), RoundedCornerShape(22.dp))
            .padding(23.dp)
    ) {
        Text("DASHFORGE", color = BLUE, fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp)
        Spacer(Modifier.height(12.dp))
        Text("Your game.\nYour control.", fontSize = 31.sp,
            lineHeight = 36.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(10.dp))
        Text("Your Geometry Dash companion.",
            color = Color(0xFFB9CBE5), fontSize = 13.sp)
    }
}

@Composable
fun GeometryDashCard() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val launchIntent = remember {
        context.packageManager.getLaunchIntentForPackage(
            "com.robtopx.geometryjump"
        )
    }

    val installed = launchIntent != null

    Column(
        Modifier.fillMaxWidth()
            .background(PANEL, RoundedCornerShape(20.dp))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Outlined.SportsEsports,
                contentDescription = null,
                tint = BLUE,
                modifier = Modifier.size(30.dp)
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text("GEOMETRY DASH", fontWeight = FontWeight.Bold)
                Text(
                    if (installed) "Game detected" else "Game not detected",
                    color = if (installed) Color(0xFF55D98A)
                    else Color(0xFFFF7777),
                    fontSize = 12.sp
                )
            }
        }

        Button(
            onClick = {
                launchIntent?.let { context.startActivity(it) }
            },
            enabled = installed,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (installed) "Launch Geometry Dash" else "Not installed")
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(modifier.background(PANEL, RoundedCornerShape(18.dp)).padding(16.dp)) {
        Icon(icon, null, tint = BLUE)
        Spacer(Modifier.height(12.dp))
        Text(value, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Text(title, fontSize = 12.sp, color = MUTED)
    }
}

@Composable
fun FeatureCard(title: String, description: String, icon: ImageVector) {
    Row(
        Modifier.fillMaxWidth()
            .background(PANEL, RoundedCornerShape(17.dp))
            .padding(17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = BLUE, modifier = Modifier.size(28.dp))
        Spacer(Modifier.width(15.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(description, color = MUTED, fontSize = 12.sp)
        }
        Icon(Icons.Outlined.ChevronRight, null, tint = MUTED)
    }
}

@Composable
fun HomeScreen() {
    LazyColumn(
        Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("DASHFORGE", color = BLUE, fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp)
                    Text("Welcome back.", fontSize = 26.sp,
                        fontWeight = FontWeight.Bold)
                }
                Icon(Icons.Outlined.Bolt, null, tint = BLUE,
                    modifier = Modifier.size(36.dp))
            }
        }
        item { HeroCard() }
        item { GeometryDashCard() }
        item { Text("OVERVIEW", color = BLUE, fontWeight = FontWeight.Bold) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Installed mods", "0", Icons.Outlined.Extension,
                    Modifier.weight(1f))
                StatCard("App version", "0.1", Icons.Outlined.Code,
                    Modifier.weight(1f))
            }
        }
        item { Text("QUICK ACCESS", color = BLUE, fontWeight = FontWeight.Bold) }
        item { FeatureCard("Mod Menu", "Manage your modules", Icons.Outlined.Tune) }
        item { FeatureCard("Mod Library", "Discover new modules", Icons.Outlined.Apps) }
        item { FeatureCard("Developer Mode", "Coming soon", Icons.Outlined.Code) }
        item {
            Text("DASHFORGE v0.1.0 • ANDROID",
                color = MUTED, fontSize = 10.sp)
        }
    }
}

@Composable
fun ModsScreen() {
    var installed by remember {
        mutableStateOf(ModuleManager.getInstalled().toSet())
    }

    val modules = ModuleRegistry.getAll()

    LazyColumn(
        Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Heading("Mod Menu", "Manage your DashForge modules")
        }

        item {
            Text(
                "${installed.size} MODULES INSTALLED",
                color = BLUE,
                fontWeight = FontWeight.Bold
            )
        }

        if (modules.isEmpty()) {
            item {
                EmptyCard(
                    Icons.Outlined.Extension,
                    "No modules available",
                    "Check back later."
                )
            }
        }

        items(modules, key = { it.id }) { module ->
            val isInstalled = module.id in installed

            Column(
                Modifier.fillMaxWidth()
                    .background(PANEL, RoundedCornerShape(18.dp))
                    .padding(17.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Extension,
                        null,
                        tint = BLUE,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(Modifier.width(12.dp))

                    Column(Modifier.weight(1f)) {
                        Text(module.name, fontWeight = FontWeight.Bold)
                        Text(
                            "v${module.version} • ${module.author}",
                            color = MUTED,
                            fontSize = 11.sp
                        )
                    }
                }

                Text(
                    module.description,
                    color = MUTED,
                    fontSize = 13.sp
                )

                Button(
                    onClick = {
                        if (isInstalled) {
                            ModuleManager.uninstall(module.id)
                        } else {
                            ModuleManager.install(module.id)
                        }

                        installed = ModuleManager.getInstalled().toSet()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isInstalled) "Uninstall" else "Install")
                }

                Text(
                    if (isInstalled) "Installed" else "Available",
                    color = if (isInstalled) Color(0xFF55D98A) else MUTED,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun LibraryScreen() {
    Column(
        Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Heading("Mod Library", "Discover new modules")
        EmptyCard(Icons.Outlined.Search, "Library is empty",
            "The module library is coming soon.")
    }
}

@Composable
fun EmptyCard(icon: ImageVector, title: String, description: String) {
    Column(
        Modifier.fillMaxWidth()
            .background(PANEL, RoundedCornerShape(20.dp))
            .padding(27.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, null, tint = BLUE, modifier = Modifier.size(42.dp))
        Spacer(Modifier.height(14.dp))
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(description, color = MUTED, fontSize = 12.sp)
    }
}

@Composable
fun SettingsScreen() {
    var animations by remember { mutableStateOf(true) }
    var notifications by remember { mutableStateOf(true) }

    Column(
        Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Heading("Settings", "Customize your experience")
        SettingSwitch("Animations", "Enable interface animations",
            animations) { animations = it }
        SettingSwitch("Notifications", "Receive notifications",
            notifications) { notifications = it }
        Text("ABOUT", color = BLUE, fontWeight = FontWeight.Bold)
        FeatureCard("DashForge", "Version 0.1.0", Icons.Outlined.Info)
        FeatureCard("Developer Mode", "Coming soon", Icons.Outlined.Code)
    }
}

@Composable
fun SettingSwitch(
    title: String,
    description: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth()
            .background(PANEL, RoundedCornerShape(16.dp))
            .padding(17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(description, color = MUTED, fontSize = 12.sp)
        }
        Switch(checked = checked, onCheckedChange = onChange)
    }
}
