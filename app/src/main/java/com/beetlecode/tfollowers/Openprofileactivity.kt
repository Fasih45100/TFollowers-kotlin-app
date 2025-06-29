package com.beetlecode.tfollowers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.beetlecode.tfollowers.database.MyDatabase
import com.beetlecode.tfollowers.database.MyRepository
import com.beetlecode.tfollowers.database.Post
import com.beetlecode.tfollowers.utill.FileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Openprofileactivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var joinDateTextView: TextView
    private lateinit var websiteTextView: TextView
    private lateinit var birthdayTextView: TextView
    private lateinit var followingTextView: TextView
    private lateinit var followersTextView: TextView
    private lateinit var cvrImage: ImageView
    private lateinit var prImage: ImageView
    private lateinit var popoption: ImageView

    private lateinit var sharedPreferences: SharedPreferences

    private var following: String? = null
    private var followers: String? = null
    private var name: String? = null
    private var joinDate: String? = null
    private var website: String? = null
    private var birthday: String? = null
    private var cvrURi: String? = null
    private var prUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.openprofileactivity)


        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)

        following = sharedPreferences.getString("following", null)
        followers = sharedPreferences.getString("followers", null)
        name = sharedPreferences.getString("name", null)
        joinDate = sharedPreferences.getString("joinDate", null)
        website = sharedPreferences.getString("website", null)
        birthday = sharedPreferences.getString("birthday", null)
        cvrURi = sharedPreferences.getString("cover_image", null)
        prUri = sharedPreferences.getString("prImage", null)





        followingTextView = findViewById(R.id.tvadfollowing)
        followersTextView = findViewById(R.id.tvadfollowers)
        nameTextView = findViewById(R.id.TVName)
        joinDateTextView = findViewById(R.id.TVjoin_date)
        websiteTextView = findViewById(R.id.TVwebsite)
        birthdayTextView = findViewById(R.id.TVbirth_date)
        cvrImage = findViewById(R.id.cover_image1)
        prImage = findViewById(R.id.profile_image_inOP)
        popoption = findViewById(R.id.popoption_in_OP)
        cvrImage.setImageResource(R.drawable.img)

        cvrURi?.let { cvrImage.setImageURI(it.toUri()) }
        prUri?.let { prImage.setImageURI(it.toUri()) }


        val backImage = findViewById<ImageView>(R.id.imageView)
        backImage.setOnClickListener {
            finish()
        }


        val textView = findViewById<TextView>(R.id.textView55)
        textView.setOnClickListener {



        }
        popoption.setOnClickListener {
            showPopupMenu(it)
        }
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview_post_in_activity_OP)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val db = MyDatabase(this)
        val repo = MyRepository(db.getMyDao())
        CoroutineScope(Dispatchers.Main).launch {
            repo.getAllPost().observe(this@Openprofileactivity) {

                Log.d("TAG", "onCreate: called in rec")
                if (it.isEmpty()){
                } else {
                    val adopter = PostRecyclerView(this@Openprofileactivity, it, repo)
                    recyclerview.adapter = adopter
                }

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
        following = sharedPreferences.getString("following", null)
        Log.d("TAG", "onResume: $following")
        following?.let {
            followingTextView.text = following
        }
        followers = sharedPreferences.getString("followers", null)
        Log.d("TAG", "onResume: $followers")
        followers?.let {
            followersTextView.text = followers
        }
        joinDate = sharedPreferences.getString("joinDate", null)
        Log.d("TAG", "onResume: $joinDate")
        joinDate?.let {
            joinDateTextView.text = joinDate
        }
        website = sharedPreferences.getString("website", null)
        Log.d("TAG", "onResume: $website")
        website?.let {
            websiteTextView.text = website
        }
        birthday = sharedPreferences.getString("birthday", null)
        Log.d("TAG", "onResume: $birthday")
        birthday?.let {
            birthdayTextView.text = birthday
        }

        cvrURi = sharedPreferences.getString("coverimage", null)
        prUri = sharedPreferences.getString("primage", null)

        cvrURi?.let {
//            val uu = FileUtils().getPath(this, it.toUri())
//            Log.d("TAG", "onResume: cvr $uu, $it")
            Glide.with(this).load(it).into(cvrImage);

        }

        prUri?.let {
//            val uu = FileUtils().getFile(this, it.toUri())
//            Log.d("TAG", "onResume: $uu , $it")
            Glide.with(this).load(it).into(prImage);
        } ?: run {
            prImage.setImageResource(R.drawable.avatar)
        }

        val isVerified = sharedPreferences.getBoolean("verified", false)
        val blueTick = findViewById<ImageView>(R.id.image_Tbluetick_in_OPA)
        if (isVerified)
            blueTick.visibility = View.VISIBLE
        else
            blueTick.visibility = View.INVISIBLE
    }

    fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_right)

        // Set a click listener for the menu items

        popupMenu.show()
    }
}