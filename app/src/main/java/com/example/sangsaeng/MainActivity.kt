package com.example.sangsaeng


import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.edit_id)
        val password = findViewById<EditText>(R.id.edit_pw)

        //로그인
        btn_login.setOnClickListener {

            if (email.text.toString().length == 0 || password.text.toString().length == 0) {
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(null)
                        }

                    }
            }

        }
        btn_register.setOnClickListener {
            Log.d(TAG,"회원가입 클릭")
            val register_UI : Intent = Intent(this, RegisterActivity::class.java)
            startActivity(register_UI)

        }

        btn_logout.setOnClickListener {
                auth.signOut()

            tv_message.setText("로그인이 필요합니다..")
            btn_logout.isEnabled = false
            btn_login.isEnabled = true
            btn_register.isEnabled = true
        }

    }
    override fun onResume() {
        super.onResume()
        val currentUser = auth?.currentUser
        updateUI(currentUser)

    }

    override fun onStart() {
        super.onStart()
        //앱 시작 단계에서 사용자가 현재 로그인 되어 있는지 확인하고 처리 해 준다.
        val currentUser = auth?.currentUser
        updateUI(currentUser) //이건 원하는대로 사용자 설정해 주는 부분인듯 하다.
    }

    fun updateUI(cUser : FirebaseUser? = null){
        if(cUser != null) {
            tv_message.setText("로그인 되었습니다.")

            btn_login.isEnabled = false
            btn_register.isEnabled = false
            btn_logout.isEnabled = true
        } else {
            tv_message.setText("로그인이 필요합니다..")
            btn_logout.isEnabled = false
        }
        edit_id.setText("")
        edit_pw.setText("")
        hideKeyboard(edit_id)
        // Toast.makeText(this, "유저: "+cUser.toString(), Toast.LENGTH_SHORT).show()
    }

    fun hideKeyboard(view: View) {
        view?.apply {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}