package g7.project.group7android

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OrderActivity : AppCompatActivity() {
    private lateinit var rvPrice: RecyclerView
    private lateinit var priceAdapter: priceAdapter
    private val priceList = listOf(
        dcPrice("Ultimate Experience", "Rp. 15.000.000"),
        dcPrice("My Universe", "Rp. 10.000.000"),
        dcPrice("Festival", "Rp. 8.000.000"),
        dcPrice("CAT 1", "Rp. 5.000.000"),
        dcPrice("CAT 2", "Rp. 4.200.000"),
        dcPrice("CAT 3", "Rp. 3.000.000"),
        dcPrice("CAT 4", "Rp. 2.500.000"),
        dcPrice("CAT 5", "Rp. 2.000.000"),
        dcPrice("CAT 6", "Rp. 1.700.000")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        rvPrice = findViewById(R.id.rvPrice)
        priceAdapter = priceAdapter(priceList)

        rvPrice.layoutManager = LinearLayoutManager(this)
        rvPrice.adapter = priceAdapter


    }
}