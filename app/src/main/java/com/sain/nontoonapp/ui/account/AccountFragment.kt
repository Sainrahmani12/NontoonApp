package com.sain.nontoonapp.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.sain.nontoonapp.R
import com.sain.nontoonapp.activity.ChangePasswordActivity
import com.sain.nontoonapp.activity.ChangeProfileActivity
import com.sain.nontoonapp.activity.LoginActivity
import com.sain.nontoonapp.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentAccountBinding? = null
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {
            tombolKeluar()
        }

        binding.btnChangeProfile.setOnClickListener {
            val i = Intent(context, ChangeProfileActivity::class.java)
            startActivity(i)
        }

        binding.btnChangePassword.setOnClickListener {
            val i = Intent(context, ChangePasswordActivity::class.java)
            startActivity(i)
        }

        binding.btnChangeProfile.setOnClickListener {
            val i = Intent(context, ChangeProfileActivity::class.java)
            startActivity(i)
        }
    }

    private fun tombolKeluar() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val i = Intent(context, LoginActivity::class.java)
        startActivity(i)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}