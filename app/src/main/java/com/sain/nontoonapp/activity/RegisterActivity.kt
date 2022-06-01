package com.sain.nontoonapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import com.sain.nontoonapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.sain.nontoonapp.ui.home.HomeFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        binding.tvSignin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnRegister.setOnClickListener {

            val name = binding.edtNamaRegister.text.toString()
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()

            if (name.isEmpty()){
                binding.edtNamaRegister.error = "Nama harus diisi!"
                binding.edtNamaRegister.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()){
                binding.edtEmailRegister.error = "Email harus diisi!"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailRegister.error = "Email tidak valid!"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                binding.edtPasswordRegister.error = "Password harus diisi!"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6){
                binding.edtPasswordRegister.error = "Password minimal 6 karakter!"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }

            userFirebase(email, password)
        }
    }

    private fun userFirebase(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){

//                    Toast.makeText(this,"$email berhasil didaftarkan!", Toast.LENGTH_SHORT).show()
//                    val i = Intent(this, HomeFragment::class.java)
//                    startActivity(i)
//                    finish()

                    Intent(this, HomeFragment::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

                }else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null){
            val i = Intent(this, MainActivity::class.java)
            Toast.makeText(this,"Welcome, ${FirebaseAuth.getInstance().currentUser?.email}", Toast.LENGTH_SHORT).show()
            startActivity(i)
            finish()
        }
    }
}