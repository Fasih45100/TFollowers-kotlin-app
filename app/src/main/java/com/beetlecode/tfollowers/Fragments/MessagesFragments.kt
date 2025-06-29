package com.beetlecode.tfollowers.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.beetlecode.tfollowers.EditProfileActivity
import com.beetlecode.tfollowers.R
import com.beetlecode.tfollowers.profileActivity

class MessagesFragments : Fragment() {
    private lateinit var writemessage:TextView
    private lateinit var settingicon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.messages_fragment, container, false)
        settingicon = v.findViewById(R.id.setting_icon_in_MF)
        writemessage = v.findViewById(R.id.textView3)

        writemessage.setOnClickListener {
            val toastMessage = "Write Message Clicked"
            Toast.makeText(requireActivity(), toastMessage, Toast.LENGTH_SHORT).show()
        }

        settingicon.setOnClickListener {
            // Start the EditProfileActivity when the TextView is clicked.
            val intent = Intent(requireActivity(), profileActivity::class.java)
            startActivity(intent)
        }

        return v


    }
}