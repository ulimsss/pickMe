package com.example.sangsaeng

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG : String = "Register"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.edt_email)
        val password = findViewById<EditText>(R.id.edt_password)

        btn_sign_in.setOnClickListener {
            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요. ",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                    .addOnCompleteListener(this) {task ->
                        if (task.isSuccessful){
                            Log.d(TAG,"createUserWithEmail : sucess")
                            val user = auth.currentUser
                            finish()
                        }else{
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,"Authentication failed",
                                Toast.LENGTH_SHORT
                            ).show()
                            email?.setText("")
                            password?.setText("")
                            email.requestFocus()
                        }
                    }
            }
        }
    }

}