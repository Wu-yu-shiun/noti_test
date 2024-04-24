package com.example.noti_test1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val inputField = findViewById<EditText>(R.id.inputField)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val responseView = findViewById<TextView>(R.id.responseView)

        sendButton.setOnClickListener {
            val userInput = inputField.text.toString()
            if (userInput.isBlank()) {
                responseView.text = "Please enter some text"
                return@setOnClickListener
            }
            // responseView.text = "Sending your text...$userInput"

            RetrofitClient.instance.submitText(RequestBody(userInput))
                .enqueue(object : Callback<ResponseData> {
                    override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                        if (response.isSuccessful) {
                            responseView.text = response.body()?.message
                        } else {
                            val errorMessage = response.errorBody()?.string() ?: "Unknown server error"
                            responseView.text = "Error: ${response.code()} - $errorMessage"
                            Log.e("API_ERROR", "Server error: $errorMessage")
                        }
                    }

                    override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                        responseView.text = "Error: ${t.message}"
                        Log.e("API_FAILURE", "Network failure: ${t.message}")
                    }
                })
            inputField.text.clear()
        }
    }
}