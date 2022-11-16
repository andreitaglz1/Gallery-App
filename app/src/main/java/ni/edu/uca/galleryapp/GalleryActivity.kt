package ni.edu.uca.galleryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import ni.edu.uca.galleryapp.adapter.MyAdapter
import ni.edu.uca.galleryapp.database.MyDBHandler
import ni.edu.uca.galleryapp.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    lateinit var  binding : ActivityGalleryBinding
    lateinit var db : MyDBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MyDBHandler(this)
        val images = db.getAllImages()
        binding.rcvgallery.adapter = MyAdapter(images, this)
        binding.rcvgallery.layoutManager = GridLayoutManager(this, 3)
    }
}