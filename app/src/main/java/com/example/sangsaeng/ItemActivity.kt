package com.example.sangsaeng

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging

class ItemActivity : AppCompatActivity() {
    //  토큰 출력하기
//    private val resultTextView: TextView by lazy {
//        findViewById(R.id.resultTextView)
//    }
//
//    private val firebaseTokenTextView: TextView by lazy {
//        findViewById(R.id.firebaseTokenTextView)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        initFirebase()
     //   updateResult()

        //pushtest에 test1이라고 설정해서 데이터베이스에 저장
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef : DatabaseReference = database.getReference("pushtest")
    //    myRef.setValue("test123")



//        val addRef = FirebaseDatabase.getInstance().getReference("pushtest-8dd92-default-rtdb").push()
//        addRef.setValue("hi")

        //pushtest에 있는 것 읽어와서 출력
        FirebaseDatabase.getInstance().getReference("pushtest")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot?.let {
                        val data = it.getValue()
                        val textView:TextView = findViewById(R.id.message)
                        textView.text = data.toString()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    error.toException().printStackTrace()
                }


            })

    }
    private fun initFirebase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener{
                task -> if(task.isSuccessful){
           // firebaseTokenTextView.text = task.result
        }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent) // 새로 들어온 인텐트로 교체

       // updateResult(true)
    }

//    private fun updateResult(isNewIntent: Boolean = false) {
//        // isNewIntent 앱이 새로 켜진건지
//        resultTextView.text =
//            intent.getStringExtra("notificationType") ?: "앱 런처" + if (isNewIntent) {
//                "(으)로 갱신했습니다."
//            } else {
//                "(으)로 실행했습니다."
//            }
//    }
}