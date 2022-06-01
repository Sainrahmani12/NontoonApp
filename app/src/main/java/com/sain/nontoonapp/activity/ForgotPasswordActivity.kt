package com.sain.nontoonapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sain.nontoonapp.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgotPasswordBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.btnResetPass.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val edtEmail = binding.edtEmail

            if (email.isEmpty()) {
                edtEmail.error = "Please fill your email"
                edtEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.error = "email tidak valid"
                edtEmail.requestFocus()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Verifikasi kamu berhasil di kirim ke $email",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    edtEmail.error = "${it.exception?.message}"
                    edtEmail.requestFocus()
                }
            }
        }
    }
}