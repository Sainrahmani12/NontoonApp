package com.sain.nontoonapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sain.nontoonapp.databinding.ActivityLoginBinding
import com.sain.nontoonapp.ui.home.HomeFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvSignup.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

        binding.tvForgot.setOnClickListener {
            val i = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(i)
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()


            if (email.isEmpty()) {
                binding.edtEmailLogin.error = "Email harus diisi!"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtEmailLogin.error = "Email tidak terdaftar!"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edtPasswordLogin.error = "Password harus diisi!"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.edtPasswordLogin.error = "Password minimal 6 karakter"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            loginUserToFirebase(email, password)
            finish()
        }
    }

    private fun loginUserToFirebase(email: String, password: Any) {
        auth.signInWithEmailAndPassword(email, password.toString())
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val i = Intent(this, HomeFragment::class.java)
                    Toast.makeText(this, "Welcome, $email", Toast.LENGTH_SHORT).show()
                    startActivity(i)

                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}