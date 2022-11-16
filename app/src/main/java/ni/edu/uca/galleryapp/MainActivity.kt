package ni.edu.uca.galleryapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import ni.edu.uca.galleryapp.database.MyDBHandler
import ni.edu.uca.galleryapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var db : MyDBHandler

    companion object {
        const val GALLERY_CODE = 100
        const val CAMERA_CODE = 200
        var hasImage = false
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MyDBHandler(this)

        binding.btnGallery.setOnClickListener {
            openGallery()
        }
        binding.btnCam.setOnClickListener {
            openCam()
        }
        binding.btnGuardar.setOnClickListener {
            saveImage()
        }
        binding.btngotoGallery.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveImage() {
        if (hasImage){
            val bitmap =( binding.imageView.drawable as BitmapDrawable).bitmap
                if(db.addImage(bitmap)){
                     Toast.makeText(this, "image is saved", Toast.LENGTH_SHORT).show()
                    binding.imageView.setImageResource(R.drawable.image_no_supported)
                    hasImage = false
                }
                }
    }

    private fun openCam() {
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, CAMERA_CODE)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@MainActivity,
                        "Sorry, you can't use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1!!.continuePermissionRequest()
                }

            }).check()
    }

    private fun openGallery() {
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, GALLERY_CODE)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(
                        this@MainActivity,
                        "Sorry, you can't use this feature",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1!!.continuePermissionRequest()
                }

            }).check()
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GALLERY_CODE ->{
                if (resultCode == Activity.RESULT_OK){
                    val imgUri = data!!.data
                    binding.imageView.setImageURI(imgUri)
                    hasImage = true
                }
            }
            CAMERA_CODE ->{
                 if (resultCode == Activity.RESULT_OK && data!!.extras != null){
                     val img= data!!.extras!!.get("data") as  Bitmap
                     binding.imageView.setImageBitmap(img)
                     hasImage = true
                 }
            }
        }
     }
   }
