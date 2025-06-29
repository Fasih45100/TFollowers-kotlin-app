package com.beetlecode.tfollowers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.github.drjacky.imagepicker.constant.ImageProvider
import com.google.android.material.switchmaterial.SwitchMaterial

class EditProfileActivity : AppCompatActivity() {
    private lateinit var followingEditText: EditText
    private lateinit var followersEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var joinDateEditText: EditText
    private lateinit var websiteEditText: EditText
    private lateinit var birthdayEditText: EditText
    private lateinit var saveTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var cvrimage: ImageView
    private var isverified: Boolean = false
    private lateinit var primage: ImageView
    private var prUri: String? = null
    private var cvrUri: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.beetlecode.tfollowers.R.layout.activity_edit_profile)

        sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val pr: ImageView = findViewById(com.beetlecode.tfollowers.R.id.btn_img_pick_profile)
        cvrimage = findViewById(com.beetlecode.tfollowers.R.id.cover_image)
        cvrimage.setImageResource(com.beetlecode.tfollowers.R.drawable.img)
        primage = findViewById(com.beetlecode.tfollowers.R.id.profile_image_inEP)


        val switchButton: SwitchMaterial = findViewById(com.beetlecode.tfollowers.R.id.switchbtn)
        switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            isverified = isChecked
        }
        followingEditText = findViewById(com.beetlecode.tfollowers.R.id.ETFollowing)
        followersEditText = findViewById(com.beetlecode.tfollowers.R.id.ETFollowers)
        nameEditText = findViewById(com.beetlecode.tfollowers.R.id.ETName)
        joinDateEditText = findViewById(com.beetlecode.tfollowers.R.id.ETjoin_date)
        websiteEditText = findViewById(com.beetlecode.tfollowers.R.id.ETWebsite)
        birthdayEditText = findViewById(com.beetlecode.tfollowers.R.id.ETBirthday)
        saveTextView = findViewById(com.beetlecode.tfollowers.R.id.TVsave)

        val following = sharedPreferences.getString("following", null)
        followingEditText.setText(following)
        val followers = sharedPreferences.getString("followers", null)
        followersEditText.setText(followers)
        val name = sharedPreferences.getString("name", null)
        nameEditText.setText(name)
        val joinDate = sharedPreferences.getString("joinDate", null)
        joinDateEditText.setText(joinDate)
        val website = sharedPreferences.getString("website", null)
        websiteEditText.setText(website)
        val birthday = sharedPreferences.getString("birthday", null)
        birthdayEditText.setText(birthday)

        val ucvr = sharedPreferences.getString("coverimage", null)
        val upr = sharedPreferences.getString("primage", null)
        prUri = upr
        cvrUri = ucvr

        if (prUri?.contains("jpg", true) == true) {
            Glide.with(this).load(prUri).into(primage)
        }
        if (cvrUri?.contains("jpg", true) == true) {
            Glide.with(this).load(cvrUri).into(cvrimage)
        }


        val backImage = findViewById<ImageView>(com.beetlecode.tfollowers.R.id.IVback)
        backImage.setOnClickListener {
            finish()
        }


    }

    private fun uploadPrImage() {
        ImagePicker.with(this)
            .provider(ImageProvider.BOTH) //Or bothCameraGallery()
            .createIntentFromDialog { launcherPr.launch(it) }
    }

    private fun uploadCvrImage() {
        ImagePicker.with(this)
            .provider(ImageProvider.BOTH) //Or bothCameraGallery()
            .createIntentFromDialog { launcherCvr.launch(it) }
    }

    private val launcherCvr =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                // Use the uri to load the image
                // Only if you are not using crop feature:
                uri.let { galleryUri ->
//                    val uu = FileUtils().getFile(this, galleryUri)
                    cvrUri = copyImageToPrivateStorage(this, galleryUri)
                    Glide.with(this).load(cvrUri).into(cvrimage);
                    Log.d("TAG", "path: $cvrUri")
                }
            }
        }
    private val launcherPr =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                // Use the uri to load the image
                // Only if you are not using crop feature:
                uri.let { galleryUri ->
//                    val uu = FileUtils().getFile(this, galleryUri)
                    prUri = copyImageToPrivateStorage(this, galleryUri)
                    Glide.with(this).load(prUri).into(primage)
                    Log.d("TAG", "path: $prUri")
                }
            }
        }


    fun copyImageToPrivateStorage(context: Context, imageUri: Uri): String? {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val fileName = "image_${System.currentTimeMillis()}.jpg"
        val privateDir = context.getExternalFilesDir(null)
        val file = File(privateDir, fileName)

        try {
            val outputStream = FileOutputStream(file)
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var bytesRead: Int

            while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.close()
            inputStream?.close()

            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }


}