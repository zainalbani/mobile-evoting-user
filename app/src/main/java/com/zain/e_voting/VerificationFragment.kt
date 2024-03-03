package com.zain.e_voting

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zain.e_voting.databinding.FragmentVerificationBinding


class VerificationFragment : DialogFragment() {
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog!!.window
            ?.apply {
                setLayout(
                    350.toPx(requireContext()),
                    400.toPx(requireContext())
                )
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToHome.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun Int.toPx(requireContext: Context): Int =
        (this * requireContext.resources.displayMetrics.density).toInt()

}

