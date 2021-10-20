package com.example.practicefrombook1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var cheatButton: Button
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private var currentQuestion: Int = 0
    private var currentAnswer: Int = 0
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: called")
        setContentView(R.layout.activity_main)

        /*
        val provider: ViewModelProvider = ViewModelProvider(this)
        val quizViewModel = provider.get(QuizViewModel::class.java)
        Log.d(TAG, "QuizViewModel $quizViewModel")
        */

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        getViewById()
        getListeners()
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
    }

    override fun onSaveInstanceState(saveInstanceState: Bundle) {
        super.onSaveInstanceState(saveInstanceState)
        Log.i(TAG, "onSaveInstanceState")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: called")
    }

    private fun getViewById() {
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_textView)
    }

    private fun getListeners() {
        cheatButton.setOnClickListener {

            val intent = Intent(this, CheatActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show()
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
            currentQuestion += 1
            buttonIsVisible(false)
            isAnswered()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            currentQuestion += 1
            buttonIsVisible(false)
            isAnswered()
        }
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            Toast.makeText(this, "${quizViewModel.currentIndex}", Toast.LENGTH_SHORT).show()
            updateQuestion()
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToPref()
            Toast.makeText(this, "${quizViewModel.currentIndex}", Toast.LENGTH_SHORT).show()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        // Log.d(TAG, "Updating Question text ", Exception())
        val questionTextResId =
            quizViewModel.currentQuestionText //обращаемся к списку по индексу и получаем значение textResId
        questionTextView.setText(questionTextResId)  //Присваиваем значение
        buttonIsVisible(true)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        when (userAnswer == quizViewModel.currentQuestionAnswer) {
            true-> {
                currentAnswer += 1
                Toast.makeText(this, "$currentAnswer", Toast.LENGTH_SHORT).show()}
            false -> {
                Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isAnswered() {
        if (currentQuestion == quizViewModel.questionSize) {
            Toast.makeText(
                this,
                "Game is over, you've got $currentAnswer of ${quizViewModel.questionSize} points",
                Toast.LENGTH_SHORT
            ).show()
            currentQuestion = 0
            currentAnswer = 0
            buttonIsVisible(true)
        }
    }

    private fun buttonIsVisible(isVisible: Boolean) {
        trueButton.isEnabled = isVisible
        falseButton.isEnabled = isVisible
    }

}