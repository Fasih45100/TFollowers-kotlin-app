package com.beetlecode.tfollowers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beetlecode.tfollowers.Fragments.HomeFragments
import com.beetlecode.tfollowers.Fragments.MessagesFragments
import com.beetlecode.tfollowers.Fragments.NotificationFragments
import com.beetlecode.tfollowers.Fragments.SearchFragments
import com.bumptech.glide.Glide
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity_second : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var prImage: ImageView
    private lateinit var sharedPreferences: SharedPreferences


    private var prUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_second)

        Fresco.initialize(this)

        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        prUri = sharedPreferences.getString("primage", null)
        prImage = findViewById(R.id.profile_image)





        val hm = HomeFragments()
        var fragment: Fragment? = hm
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment!!)
            .commit()

        bottomNavigationView = findViewById(R.id.nav_view)


        prImage.setOnClickListener {
            val intent = Intent(this, profileActivity::class.java)
            startActivity(intent)
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        bottomNavigationView.setOnItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.nav_home -> {
                    fragment = HomeFragments()
                }

                R.id.nav_search -> {
                    fragment = SearchFragments()
                }

                R.id.nav_notification -> {
                    fragment = NotificationFragments()
                }

                R.id.nav_inbox -> {
                    fragment = MessagesFragments()
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment!!)
                .commit()
            return@setOnItemSelectedListener true
        }
    }


    override fun onResume() {
        super.onResume()

        prUri = sharedPreferences.getString("primage", null)
        try {
            prUri?.let {
//                val uu = FileUtils().getUri(File( it))
//                Log.d("TAG", "onResume: uri: $uu")
                Glide.with(this).load(it).into(prImage)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun showFrg(frg: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(android.R.id.content, frg)
            .commit()
    }
}