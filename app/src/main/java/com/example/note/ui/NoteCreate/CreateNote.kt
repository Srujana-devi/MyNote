package com.example.note.ui.NoteCreate

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.note.PhotoNotesApp
import com.example.note.R
import com.example.note.ui.GenericAppBar
import com.example.note.ui.NoteList.NotesFab
import com.example.note.ui.NotesViewModel
import com.example.note.ui.theme.PhotoNotesTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CreateNoteScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {


    val currentNote = remember { mutableStateOf("") }
    val currentTitle = remember { mutableStateOf("") }
    val currentPhotos = remember { mutableStateOf("") }
    val saveButtonState = remember { mutableStateOf(false) }


    val getImageRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) {
        if (it != null) {
            PhotoNotesApp.getUriPermission(it)
        }
        currentPhotos.value = it.toString()
    }




    PhotoNotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
            Scaffold(
                topBar = {
                    GenericAppBar(
                        title = "Create Note",
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.save),
                                contentDescription = stringResource(R.string.save_note),
                                tint = Color.Black,
                            )
                        },
                        onIconClick = {
                            viewModel.createNote(
                                currentTitle.value,
                                currentNote.value,
                                currentPhotos.value
                            )
                            navController.popBackStack()
                        },
                        iconState = saveButtonState
                    )
                },
                floatingActionButton = {
                    NotesFab(
                        contentDescription = stringResource(R.string.add_image),
                        action = {
                            getImageRequest.launch(arrayOf("image/*"))

                        },
                        icon = R.drawable.camera
                    )
                },

                content = {
                    Column(
                        Modifier
                            .padding(12.dp)
                            .fillMaxSize()
                    ) {
                        if (currentPhotos.value.isNotEmpty()) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(data = Uri.parse(currentPhotos.value))
                                        .build()
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight(0.3f)
                                    .padding(6.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        TextField(
                            value = currentTitle.value,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            onValueChange = { value ->
                                currentTitle.value = value
                                saveButtonState.value =
                                    currentTitle.value != "" && currentNote.value != ""
                            },
                            label = { Text(text = "Title") }
                        )
                        Spacer(modifier = Modifier.padding(12.dp))
                        TextField(
                            value = currentNote.value,
                            colors = TextFieldDefaults.textFieldColors(
                                cursorColor = Color.Black,
                                focusedLabelColor = Color.Black,
                            ),
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth(),
                            onValueChange = { value ->
                                currentNote.value = value
                                saveButtonState.value =
                                    currentTitle.value != "" && currentNote.value != ""
                            },
                            label = { Text(text = "Body") }
                        )
                    }
                }

            )
        }
    }
}