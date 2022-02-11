package com.example.home_slidder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    private var adapter: SliderAdapter? = null
    private var sliderDataArrayList: ArrayList<SliderData>? = null
    var db: FirebaseFirestore? = null
    private var sliderView: SliderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sliderDataArrayList = ArrayList()
        sliderView = findViewById<SliderView>(R.id.slider)
        db = FirebaseFirestore.getInstance()

        loadImages()

    }

    private fun loadImages() {
        db!!.collection("Slider").get().addOnSuccessListener { queryDocumentSnapshots ->
            // inside the on success method we are running a for loop
            // and we are getting the data from Firebase Firestore
            for (documentSnapshot in queryDocumentSnapshots) {

                // after we get the data we are passing inside our object class.
                val sliderData = documentSnapshot.toObject(SliderData::class.java)
                val model = SliderData()

                // below line is use for setting our
                // image url for our modal class.
                model.imgUrl = sliderData.imgUrl

                // after that we are adding that
                // data inside our array list.
                sliderDataArrayList!!.add(model)

                // after adding data to our array list we are passing
                // that array list inside our adapter class.
                adapter = SliderAdapter(this@MainActivity, sliderDataArrayList)

                // belows line is for setting adapter
                // to our slider view
                sliderView!!.setSliderAdapter(adapter!!)

                // below line is for setting animation to our slider.
                sliderView!!.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)

                // below line is for setting auto cycle duration.
                sliderView!!.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT

                // below line is for setting
                // scroll time animation
                sliderView!!.scrollTimeInSec = 3

                // below line is for setting auto
                // cycle animation to our slider
                sliderView!!.isAutoCycle = true

                // below line is use to start
                // the animation of our slider view.
                sliderView!!.startAutoCycle()
            }
        }.addOnFailureListener { // if we get any error from Firebase we are
            // displaying a toast message for failure
            Toast.makeText(
                this@MainActivity,
                "Fail to load slider data..",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        sliderView?.setOnClickListener {
            if (adapter?.getItemPosition(true) == 1) {
                Toast.makeText(this@MainActivity, "checked", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Fail to load slider data..", Toast.LENGTH_SHORT)
                    .show()
            }

        }}}
