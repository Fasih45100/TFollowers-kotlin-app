package com.beetlecode.tfollowers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.beetlecode.tfollowers.database.MyRepository
import com.beetlecode.tfollowers.database.Post
import com.beetlecode.tfollowers.utill.FileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class PostRecyclerView(
    val context: Context,
    val postList: List<Post>,
    val repo: MyRepository
) : RecyclerView.Adapter<PostRecyclerView.PostViewModel>() {

    class PostViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.Tname_inPVI)
        val verified = itemView.findViewById<ImageView>(R.id.image_Tbluetick_in_PIV)
        val profile = itemView.findViewById<ImageView>(R.id.profile_image_inPIV)
        val handlerName = itemView.findViewById<TextView>(R.id.handler_Name_in_PIV)
        val Tweettext = itemView.findViewById<TextView>(R.id.Tweettext)

        val postimageView = itemView.findViewById<ImageView>(R.id.image_view_inPIV)

        val commentTextView = itemView.findViewById<TextView>(R.id.TV_comment)
        val retweetTextView = itemView.findViewById<TextView>(R.id.TV_retweet)
        val likeTextView = itemView.findViewById<TextView>(R.id.TV_like)
        val ViewImageText = itemView.findViewById<TextView>(R.id.TV_view)
        val likeImage = itemView.findViewById<ImageView>(R.id.like_image_in_PIV)
        val card = itemView.findViewById<CardView>(R.id.card_view)

        val menuOptions = itemView.findViewById<ImageView>(R.id.delete_icon)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewModel {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.post_item_view, parent, false)
        return PostViewModel(itemView)
    }

    override fun onBindViewHolder(holder: PostViewModel, position: Int) {
        val pos = postList[position]
        holder.name.text = pos.firstName
        val firstFiveCharacters = pos.handlername?.take(6)
        holder.handlerName.text = "$firstFiveCharacters..."
//        val uri = pos.dpImagUri?.toUri()?.let { fileUtils.getPath(context, it) }
        holder.profile.setImageURI(pos.dpImagUri?.toUri())
        holder.Tweettext.text = pos.posttext
//        val uri2 = pos.postimage?.toUri()?.let { fileUtils.getPath(context, it) }
        if (pos.postimage == null) {
            holder.card.visibility = View.GONE
        } else {

            holder.card.visibility = View.VISIBLE
        }
        holder.postimageView.setImageURI(pos.postimage?.toUri())
        holder.commentTextView.text = pos.comment
        holder.retweetTextView.text = pos.retweet
        holder.likeTextView.text = pos.like
        holder.ViewImageText.text = pos.viewtweet



        holder.likeImage.setOnClickListener {
            holder.likeImage.setImageResource(R.drawable.tlikered)

        }
        holder.name.setOnClickListener {
            val intent = Intent(context,Openprofileactivity::class.java)
            context.startActivity(intent)
        }

        holder.menuOptions.setOnClickListener {
            showPopupMenu(it, pos)
        }


        if (pos.isVerified)
            holder.verified.visibility = View.VISIBLE
        else
            holder.verified.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return postList.size
    }


    fun showPopupMenu(view: View, item: Post) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.popup_menu)

        // Set a click listener for the menu items
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item1 -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        repo.delete(item)
                        postList.toMutableList().remove(item)
                    }
                    true
                }
                // Add more cases for other menu items if needed
                else -> false
            }
        }

        popupMenu.show()
    }

}