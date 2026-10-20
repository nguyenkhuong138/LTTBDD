@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uicomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Blue = Color(0xFF2196F3)
private val ItemBlue = Color(0xFFBBDEFB)

/* ---------- Khung chung có nút Back ---------- */
@Composable
fun DetailScaffold(title: String, onBack: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, color = Blue, fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Blue)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            content = content
        )
    }
}

/* ---------- 1. Welcome ---------- */
@Composable
fun WelcomeScreen(onReady: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(140.dp)
        )
        Spacer(Modifier.height(24.dp))
        Text("Jetpack Compose", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            "Jetpack Compose is a modern UI toolkit for building native Android " +
                    "applications using a declarative programming approach.",
            textAlign = TextAlign.Center, fontSize = 13.sp
        )
        Spacer(Modifier.height(48.dp))
        Button(onClick = onReady, modifier = Modifier.fillMaxWidth()) { Text("I'm ready") }
    }
}

/* ---------- 2. Danh sách UI Components ---------- */
private data class Item(val title: String, val desc: String, val route: String)

@Composable
fun ComponentsListScreen(onItemClick: (String) -> Unit) {
    val sections = listOf(
        "Display" to listOf(
            Item("Text", "Displays text", Routes.TEXT),
            Item("Image", "Displays an image", Routes.IMAGE)
        ),
        "Input" to listOf(
            Item("TextField", "Input field for text", Routes.TEXTFIELD),
            Item("PasswordField", "Input field for passwords", Routes.PASSWORD)
        ),
        "Layout" to listOf(
            Item("Column", "Arranges elements vertically", Routes.COLUMN),
            Item("Row", "Arranges elements horizontally", Routes.ROW)
        )
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("UI Components List", color = Blue, fontWeight = FontWeight.Medium) })
        }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            sections.forEach { (header, items) ->
                item { Text(header, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 8.dp)) }
                items(items) { ComponentRow(it.title, it.desc) { onItemClick(it.route) } }
            }
            item {
                ComponentRow("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản",
                    color = Color(0xFFF8B4B4)) { onItemClick(Routes.ALL) }
            }
        }
    }
}

@Composable
private fun ComponentRow(title: String, desc: String, color: Color = ItemBlue, onClick: () -> Unit) {
    Column(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(color)
            .clickable(onClick = onClick).padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Text(desc, fontSize = 12.sp)
    }
}

/* ---------- 3. Text Detail ---------- */
@Composable
fun TextDetailScreen(onBack: () -> Unit) {
    DetailScaffold("Text Detail", onBack) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                buildAnnotatedString {
                    append("The ")
                    withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick ") }
                    withStyle(SpanStyle(color = Color(0xFFCC7A00), fontSize = 32.sp)) { append("Brown\n") }
                    withStyle(SpanStyle(letterSpacing = 4.sp)) { append("fox jumps ") }
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) { append("over\n") }
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("the ") }
                    withStyle(SpanStyle(fontFamily = FontFamily.Cursive)) { append("lazy ") }
                    append("dog.")
                },
                fontSize = 26.sp, lineHeight = 40.sp
            )
        }
    }
}

/* ---------- 4. Image ---------- */
@Composable
fun ImageDetailScreen(onBack: () -> Unit) {
    val url = "https://s.cmx-cdn.com/giaothongvantaitphcm.edu.vn/wp-content/uploads/2024/06/ky-niem-36-nam-thanh-lap-truong-dai-hoc-giao-thong-van-tai-tphcm-560px.jpg"
    DetailScaffold("Images", onBack) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Ảnh tải từ URL (cần quyền INTERNET)
            AsyncImage(
                model = url, contentDescription = "Logo từ URL",
                modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
            Text(url, fontSize = 11.sp, textDecoration = TextDecoration.Underline, textAlign = TextAlign.Center)
            // Ảnh trong app (đặt ảnh của bạn vào res/drawable rồi đổi tên bên dưới)
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = "Ảnh trong app",
                modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Text("In app", fontSize = 12.sp)
        }
    }
}

/* ---------- 5. TextField ---------- */
@Composable
fun TextFieldDetailScreen(onBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    DetailScaffold("TextField", onBack) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            OutlinedTextField(
                value = text, onValueChange = { text = it },
                placeholder = { Text("Thông tin nhập") },
                shape = RoundedCornerShape(12.dp), singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text(
                if (text.isEmpty()) "Tự động cập nhật dữ liệu theo textfield" else text,
                color = Color.Red, fontSize = 12.sp
            )
        }
    }
}

/* ---------- PasswordField ---------- */
@Composable
fun PasswordFieldDetailScreen(onBack: () -> Unit) {
    var pass by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    DetailScaffold("PasswordField", onBack) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            OutlinedTextField(
                value = pass, onValueChange = { pass = it },
                label = { Text("Mật khẩu") }, singleLine = true,
                visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    TextButton(onClick = { visible = !visible }) { Text(if (visible) "Ẩn" else "Hiện") }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Text("Độ dài: ${pass.length} ký tự", fontSize = 12.sp, color = Color.Red)
        }
    }
}

/* ---------- Column Layout ---------- */
@Composable
fun ColumnLayoutScreen(onBack: () -> Unit) {
    DetailScaffold("Column Layout", onBack) {
        Column(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color(0xFFF0F0F0)).padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) { Box(Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFF4285F4))) }
        }
    }
}

/* ---------- 6. Row Layout ---------- */
@Composable
fun RowLayoutScreen(onBack: () -> Unit) {
    DetailScaffold("Row Layout", onBack) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(4) {
                Row(
                    Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF0F0F0)).padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val shape = RoundedCornerShape(12.dp)
                    Box(Modifier.weight(1f).height(48.dp).clip(shape).background(Color(0xFF9FBCF2)))
                    Box(Modifier.weight(1f).height(48.dp).clip(shape).background(Color(0xFF4285F4)))
                    Box(Modifier.weight(1f).height(48.dp).clip(shape).background(Color(0xFF9FBCF2)))
                }
            }
        }
    }
}

/* ---------- 7. Tự tìm hiểu: tất cả thành phần UI cơ bản ---------- */
@Composable
fun AllComponentsScreen(onBack: () -> Unit) {
    DetailScaffold("All Basic Components", onBack) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item { Group("Display") { DisplayDemo() } }
            item { Group("Input") { InputDemo() } }
            item { Group("Selection") { SelectionDemo() } }
            item { Group("Navigation") { NavigationDemo() } }
            item { Group("Content") { ContentDemo() } }
        }
    }
}

@Composable
private fun Group(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, fontWeight = FontWeight.Bold, color = Blue)
            content()
        }
    }
}

@Composable
private fun DisplayDemo() {
    Text("Text: hiển thị văn bản")
    Image(painterResource(R.drawable.ic_launcher_foreground), "Image", Modifier.size(64.dp))
}

@Composable
private fun InputDemo() {
    var tf by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var toggle by remember { mutableStateOf(false) }
    var slider by remember { mutableFloatStateOf(0.3f) }
    var showPicker by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState()

    OutlinedTextField(tf, { tf = it }, label = { Text("TextField") }, modifier = Modifier.fillMaxWidth())
    OutlinedTextField(pw, { pw = it }, label = { Text("PasswordField") },
        visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Toggle (Switch)", Modifier.weight(1f))
        Switch(toggle, { toggle = it })
    }
    Text("Slider: ${"%.0f".format(slider * 100)}")
    Slider(slider, { slider = it })
    OutlinedButton(onClick = { showPicker = true }) {
        val d = dateState.selectedDateMillis
        Text("DatePicker: " + (d?.let { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it)) } ?: "chọn ngày"))
    }
    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = { TextButton({ showPicker = false }) { Text("OK") } }
        ) { DatePicker(state = dateState) }
    }
}

@Composable
private fun SelectionDemo() {
    var checked by remember { mutableStateOf(true) }
    var radio by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Hà Nội", "TP.HCM", "Đà Nẵng")
    var picked by remember { mutableStateOf(options[1]) }

    Button(onClick = { }) { Text("Button") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked, { checked = it }); Text("Checkbox")
    }
    listOf("Lựa chọn A", "Lựa chọn B").forEachIndexed { i, label ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = radio == i, onClick = { radio = i }); Text("RadioButton: $label")
        }
    }
    // Picker: chọn giá trị từ danh sách (ExposedDropdownMenu)
    ExposedDropdownMenuBox(expanded, { expanded = it }) {
        OutlinedTextField(
            value = picked, onValueChange = {}, readOnly = true, label = { Text("Picker") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded, { expanded = false }) {
            options.forEach { o -> DropdownMenuItem(text = { Text(o) }, onClick = { picked = o; expanded = false }) }
        }
    }
}

@Composable
private fun NavigationDemo() {
    var tab by remember { mutableIntStateOf(0) }
    var menu by remember { mutableStateOf(false) }

    // NavigationView ~ TopAppBar (tiêu đề + nút back) kèm Menu
    Surface(shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, Color.LightGray)) {
        Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Blue)
            Text("NavigationView", Modifier.weight(1f).padding(start = 8.dp))
            Box {
                IconButton({ menu = true }) { Icon(Icons.Default.MoreVert, "Menu") }
                DropdownMenu(menu, { menu = false }) {
                    DropdownMenuItem(text = { Text("Sửa") }, onClick = { menu = false })
                    DropdownMenuItem(text = { Text("Xoá") }, onClick = { menu = false })
                    DropdownMenuItem(text = { Text("Chia sẻ") }, onClick = { menu = false })
                }
            }
        }
    }
    // TabBar ~ NavigationBar
    NavigationBar {
        listOf("Home" to Icons.Default.Home, "Profile" to Icons.Default.Person, "Settings" to Icons.Default.Settings)
            .forEachIndexed { i, (label, icon) ->
                NavigationBarItem(selected = tab == i, onClick = { tab = i },
                    icon = { Icon(icon, label) }, label = { Text(label) })
            }
    }
}

@Composable
private fun ContentDemo() {
    // List
    Column {
        listOf("Mục 1", "Mục 2", "Mục 3").forEach { Text("• List item: $it") }
    }
    HorizontalDivider()          // Divider
    Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {   // Card
        Text("Card: khung chứa nội dung có style", Modifier.padding(16.dp))
    }
}