package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import androidx.navigation.navArgument



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //val list = listOf("Tanu", "Tina", "Tono")
                    //Here, we call the Home composable
                    //Home(list)

//                    Home()

                    val navController = rememberNavController()
                    App(
                        navController = navController
                    )
                }
            }
        }
    }
}

data class Student(
    var name: String
)


//@Composable
//fun Home(items: List<String>) {
//    LazyColumn {
//        item {
//            Column(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = stringResource(id = R.string.enter_item))
//                TextField(
//                    value = "",
//                    onValueChange = {},
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//                Button(onClick = { }) {
//                    Text(text = stringResource(id = R.string.button_click))
//                }
//            }
//        }
//        items(items.size) { index ->
//            Column(
//                modifier = Modifier
//                    .padding(vertical = 4.dp)
//                    .fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = items[index])
//            }
//        }
//    }
//}

@Composable
fun App(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            Home{navController.navigate("resultContent/?listData=$it")}
        }
        composable(
            "resultContent/?listData={listData}",
            arguments = listOf(navArgument("listData") {
                type = NavType.StringType }
            )
        ){
            ResultContent(
                it.arguments?.getString("listData").orEmpty()
            )
        }
        }
    }






@Composable
fun Home(navigateFromHomeToResult: (String) -> Unit
) {
    val listData = remember { mutableStateListOf(
        Student("Tanu"),
        Student("Tina"),
        Student("Tono")
    )}
    var inputField = remember { mutableStateOf(Student("")) }
    HomeContent(
        listData,
        inputField.value,
        { input -> inputField.value = inputField.value.copy(name = input) },
        {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = Student("")
            }
        },
        { navigateFromHomeToResult(listData.toList().toString()) }
    )

}


@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit
){
    LazyColumn {
        item {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OnBackgroundTitleText(text = stringResource(
                    id = R.string.enter_item)
                )

                TextField(
                    value = inputField.name,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = {
                        onInputValueChange(it)
                    }
                    )

                Row {
                    PrimaryTextButton(text = stringResource(id =
                        R.string.button_click)) {
                        onButtonClick()
                    }
                    PrimaryTextButton(text = stringResource(id =
                        R.string.button_navigate)) {
                        navigateFromHomeToResult()
                    }
                }


            }

        }
        items(listData) { item ->
            Column (
                modifier = Modifier.padding(vertical = 4.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                OnBackgroundItemText(text = item.name)
            }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
//    Home(listOf("Tanu", "Tina", "Tono"))
    Home(navigateFromHomeToResult = {})
}

@Composable
fun OnBackgroundTitleText(text: String) {
    TitleText(text = text, color =
        MaterialTheme.colorScheme.onBackground)
}
//Here, we use the titleLarge style from the typography
@Composable
fun TitleText(text: String, color: Color) {
    Text(text = text, style =
        MaterialTheme.typography.titleLarge, color = color)
}

@Composable
fun OnBackgroundItemText(text: String) {
    ItemText(text = text, color =
        MaterialTheme.colorScheme.onBackground)
}
//Here, we use the bodySmall style from the typography
@Composable
fun ItemText(text: String, color: Color) {
    Text(text = text, style =
        MaterialTheme.typography.bodySmall, color = color)
}

//UI Element for displaying a button
@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    TextButton(text = text,
        textColor = Color.White,
        onClick = onClick
    )
}
//Here, we use the labelMedium style from the typography
@Composable
fun TextButton(text: String, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        colors = ButtonDefaults
            .buttonColors(
                containerColor = Color.DarkGray,
                contentColor = textColor
            )
    ) {
        Text(text = text, style =
            MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun ResultContent(listData: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Here, we call the OnBackgroundItemText UI Element
        OnBackgroundItemText(text = listData)
    }
}


