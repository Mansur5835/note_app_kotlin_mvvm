package com.example.note_app2.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.note_app2.feature_note.presentation.add_edit_note.AddEditNotePage
import com.example.note_app2.feature_note.presentation.notes.NotesPage
import com.example.note_app2.feature_note.presentation.util.Page
import com.example.note_app2.ui.theme.Note_app2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Note_app2Theme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Page.NotesPage.route
                    ) {
                        composable(
                            route = Page.NotesPage.route
                        ) {
                            NotesPage(navController = navController)
                        }

                        composable(
                            route = Page.AddEditNotePage.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor")?:-1

                            AddEditNotePage(navController = navController,
                                noteColor = color
                                )
                        }
                    }


                }
            }
        }

    }
}

