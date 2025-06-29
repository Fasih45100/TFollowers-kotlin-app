package com.beetlecode.tfollowers.Fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beetlecode.tfollowers.EditProfileActivity
import com.beetlecode.tfollowers.EditTweet_Activity
import com.beetlecode.tfollowers.PostRecyclerView
import com.beetlecode.tfollowers.R
import com.beetlecode.tfollowers.database.MyDatabase
import com.beetlecode.tfollowers.database.MyRepository
import com.beetlecode.tfollowers.utill.FileUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class HomeFragments : Fragment() {
    private val REQUEST_IMAGE_PICK = 1
    private val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2
    lateinit var blur_layout : RelativeLayout
    lateinit var dataLayout: ConstraintLayout
    lateinit var emptyLayout: ConstraintLayout
    private lateinit var button: Button



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.home_fragment, container, false)

        val titleLogTv = v.findViewById<TextView>(R.id.T_icon)
        val imageView = v.findViewById<ImageView>(R.id.T_iconX)

        val tweetbtn = v.findViewById<RelativeLayout>(R.id.tweetbtn)
        val textView1 = v.findViewById<TextView>(R.id.TVTweet)
        val textView2 = v.findViewById<TextView>(R.id.TVPhoto)
        val photobtn = v.findViewById<RelativeLayout>(R.id.photobtn)
        val edittweetbtn = v.findViewById<RelativeLayout>(R.id.edittweetbtn)
        blur_layout = v.findViewById(R.id.blur_layout)

        dataLayout = v.findViewById<ConstraintLayout>(R.id.dataLayout)
        emptyLayout = v.findViewById<ConstraintLayout>(R.id.emptyLayout)

        button = v.findViewById<Button>(R.id.Create_profile_button)


        val recyclerview = v.findViewById<RecyclerView>(R.id.recyclerview_post)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())


        val db = MyDatabase(requireContext())
        val repo = MyRepository(db.getMyDao())
        CoroutineScope(Dispatchers.Main).launch {
            repo.getAllPost().observe(viewLifecycleOwner) {

                val adopter = PostRecyclerView(requireContext(), it, repo)
                recyclerview.adapter = adopter
                if (it.isNotEmpty()){
                    recyclerview.visibility =View.VISIBLE
                }
            }
        }
        button.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }


        titleLogTv.setOnClickListener {
            // Toggle the visibility of the ImageView
            if (imageView.visibility == View.GONE) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.GONE
            }

            // Make the TextView invisible
            titleLogTv.visibility = View.INVISIBLE
        }

        edittweetbtn.setOnClickListener {
            openEditTweetActivity()
        }

        photobtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
                )
            } else {
                openGallery()
            }
        }


        tweetbtn?.setOnClickListener {
            // Toggle visibility of TextView1 and TextView2
            if (textView1?.visibility == View.GONE && textView2?.visibility == View.GONE && photobtn?.visibility == View.GONE && edittweetbtn?.visibility == View.GONE) {
                textView1.visibility = View.VISIBLE
                textView2.visibility = View.VISIBLE
                photobtn.visibility = View.VISIBLE
                edittweetbtn.visibility = View.VISIBLE
                blur_layout.visibility = View.VISIBLE
            } else {
                edittweetbtn?.visibility = View.GONE
                photobtn?.visibility = View.GONE
                textView1?.visibility = View.GONE
                textView2?.visibility = View.GONE
                blur_layout.visibility = View.GONE
            }
        }

        return v
    }

    private fun openEditTweetActivity() {
        val intent = Intent(requireContext(), EditTweet_Activity::class.java)
        startActivity(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            // Handle the selected image URI here
            // For example, you might want to display the image in an ImageView
            // imageView.setImageURI(selectedImageUri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tab = view.findViewById<TabLayout>(R.id.tab_layout)
    }


    override fun onResume() {
        super.onResume()

        val sharedPreferences = requireActivity().getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name",null)
        Log.d("TAG", "onResume:$name ")

        if (name == null){
            emptyLayout.visibility = View.VISIBLE
            dataLayout.visibility = View.GONE
        } else {

            emptyLayout.visibility = View.GONE
            dataLayout.visibility = View.VISIBLE
        }
    }
}