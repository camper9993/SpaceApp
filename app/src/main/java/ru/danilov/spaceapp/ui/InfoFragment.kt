package ru.danilov.spaceapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.danilov.spaceapp.R
import ru.danilov.spaceapp.model.Items
import ru.danilov.spaceapp.viewmodel.SpaceViewModel

class InfoFragment : Fragment() {

    lateinit var itemsList: List<Items>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageUrl = arguments!!.getString("image_url")
        val descriptionText = arguments!!.getString("description")
        val titleText = arguments!!.getString("title")

        val root =  inflater.inflate(R.layout.info_fragment, container, false)
        val imageView : ImageView = root.findViewById(R.id.image)
        val title : TextView = root.findViewById(R.id.title)
        val description : TextView = root.findViewById(R.id.description)
        Picasso.with(context).load(imageUrl).resize(1000,600).into(imageView)
        title.text = titleText
        description.text = descriptionText
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        (activity as AppCompatActivity).supportActionBar?.show()
        super.onDestroy()
    }
}