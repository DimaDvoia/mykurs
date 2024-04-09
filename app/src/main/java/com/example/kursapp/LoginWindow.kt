package com.example.kursapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kursapp.Models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginWindow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_window)

        val btnSignIn = findViewById<Button>(R.id.loginButton)
        val phone = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        val database = FirebaseDatabase.getInstance("https://kursapp-5a49d-default-rtdb.europe-west1.firebasedatabase.app/")
        val table = database.getReference("User")

        btnSignIn.setOnClickListener {
            table.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(phone.text.toString()).exists()) {
                        val user: User? = snapshot.child(phone.text.toString()).getValue(User::class.java)
                        if (password.text.toString().equals(user?.pass)) {
                            Toast.makeText(applicationContext, "Пользователь существует", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Пользователя не существует",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Пользователя не существует",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Нет интернет соединения", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }
}