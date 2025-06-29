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
import com.beetlecode.tfollowers.R
import com.beetlecode.tfollowers.profileActivity

class NotificationFragments : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.notification_fragment, container, false)

        val settingicon = v.findViewById<ImageView>(R.id.setting_icon)

        settingicon.setOnClickListener {
            // Start the EditProfileActivity when the TextView is clicked.
            val intent = Intent(requireActivity(), profileActivity::class.java)
            startActivity(intent)
        }


        return v
    }
}