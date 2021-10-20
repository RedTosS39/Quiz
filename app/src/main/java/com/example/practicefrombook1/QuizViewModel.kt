package com.example.practicefrombook1

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex: Int = 0
    private val questions = listOf(
        Question(R.string.question_1_text, false),
        Question(R.string.question_2_text, true),
        Question(R.string.question_3_text, false),
        Question(R.string.question_4_text, false),
        Question(R.string.question_5_text, false),
    )

    val questionSize = questions.size
    val currentQuestionText: Int get() = questions[currentIndex].textResId
    val currentQuestionAnswer: Boolean get() = questions[currentIndex].answer

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questions.size
    }
    fun moveToPref() {
        currentIndex = if (((currentIndex - 1) % questions.size) < 0)
            questions.size - 1
        else
            (currentIndex - 1) % questions.size
    }
}