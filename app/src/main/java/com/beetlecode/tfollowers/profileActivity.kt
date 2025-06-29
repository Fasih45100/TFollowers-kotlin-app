package com.beetlecode.tfollowers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.beetlecode.tfollowers.utill.FileUtils

class profileActivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var websiteTextView: TextView
    private lateinit var textView0: TextView
    private lateinit var textView01: TextView
    private lateinit var prImage: ImageView
    private lateinit var blueTick: ImageView

    private lateinit var TwitterBlue: TextView
    private lateinit var Topics: TextView
    private lateinit var Bookmarks: TextView
    private lateinit var Lists: TextView
    private lateinit var TwitterCircle: TextView
    private lateinit var ProffessionalTools: TextView
    private lateinit var SettingSupport: TextView
    private lateinit var theme: ImageView

    private lateinit var sharedPreferences: SharedPreferences

    private var name: String? = null
    private var website: String? = null
    private var following: String? = null
    private var followers: String? = null
    private var prUri: String? = null
    private var isVerified: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profileactivity)

        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        following = sharedPreferences.getString("following", null)
        followers = sharedPreferences.getString("followers", null)
        name = sharedPreferences.getString("name", null)
        website = sharedPreferences.getString("website", null)


        blueTick = findViewById<ImageView>(R.id.image_Tbluetick_in_PA)


        nameTextView = findViewById(R.id.cons1TV1)
        websiteTextView = findViewById(R.id.cons1TV2)
        textView0 = findViewById(R.id.cons1TV0)
        textView01 = findViewById(R.id.cons1TV01)
        prImage = findViewById(R.id.profile_image)

        TwitterBlue = findViewById(R.id.cons2TV2)
        Topics = findViewById(R.id.cons2TV3)
        Bookmarks = findViewById(R.id.cons2TV4)
        Lists = findViewById(R.id.cons2TV5)
        TwitterCircle = findViewById(R.id.cons2TV6)
        ProffessionalTools = findViewById(R.id.cons2TV7)
        SettingSupport = findViewById(R.id.cons2TV8)
        theme = findViewById(R.id.brightnessImage)

        Topics.setOnClickListener {
            // Display a toast message when the TextView is clicked.
            val toastMessage = "Topics Clicked"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
        Bookmarks.setOnClickListener {
            // Display a toast message when the TextView is clicked.
            val toastMessage = "Bookmarks Clicked"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
        Lists.setOnClickListener {
            // Display a toast message when the TextView is clicked.
            val toastMessage = "Lists Clicked"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
        TwitterCircle.setOnClickListener {
            // Display a toast message when the TextView is clicked.
            val toastMessage = "Twitter Circle Clicked"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
        ProffessionalTools.setOnClickListener {
            // Display a toast message when the TextView is clicked.
            val toastMessage = "Proffessional Tools Clicked"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
        SettingSupport.setOnClickListener {
            // Display a toast message when the TextView is clicked.
            val toastMessage = "Setting Support Clicked"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }
        theme.setOnClickListener {
            // Display a toast message when the TextView is clicked.
            val toastMessage = "Brightness theme Clicked"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }





        TwitterBlue.setOnClickListener {
            // Start the EditProfileActivity when the TextView is clicked.
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }


        val textView: TextView = findViewById(R.id.cons2TV1)
        textView.setOnClickListener { {
                val intent = Intent(this, Openprofileactivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        name = sharedPreferences.getString("name", null)
        Log.d("TAG", "onResume: $name")
        name?.let {
            nameTextView.text = name
        }
        Log.d("TAG", "onResume: uri $prUri")
        prUri = sharedPreferences.getString("primage", null)
        prUri?.let {
//            val uu = FileUtils().getFile(this, it.toUri())
            try {
                Glide.with(this).load(it).into(prImage)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        isVerified = sharedPreferences.getBoolean("verified", false)

        if (isVerified)
            blueTick.visibility = View.VISIBLE
        else
            blueTick.visibility = View.INVISIBLE

        website = sharedPreferences.getString("website", null)
        website?.let {
            websiteTextView.text = website
        }


        following = sharedPreferences.getString("following", null)
        Log.d("TAG", "onResume: $following")
        following?.let {
            textView0.text = following
        }

        followers = sharedPreferences.getString("followers", null)
        Log.d("TAG", "onResume: $followers")
        followers?.let {
            textView01.text = followers
        }
    }
}