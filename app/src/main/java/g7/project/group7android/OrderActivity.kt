package g7.project.group7android

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso


class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val namaKonser = intent.getStringExtra("namaKonser")
        val deskripsi = intent.getStringExtra("deskripsi")
        val lokasi = intent.getStringExtra("lokasi")
        val tanggal = intent.getStringExtra("tanggal")
        val jenisTiket = intent.getStringArrayListExtra("jenisTiket")
        val gambar = intent.getStringExtra("gambar")

        findViewById<TextView>(R.id.tvTitle).text = namaKonser
        findViewById<TextView>(R.id.tvDescription2).text = deskripsi
        findViewById<TextView>(R.id.tvLocation).text = lokasi
        findViewById<TextView>(R.id.tvDate).text = tanggal
        Picasso.get().load(gambar).into(findViewById<ImageView>(R.id.ivShow))

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val _price1 = findViewById<ConstraintLayout>(R.id.price1)
        val _price2 = findViewById<ConstraintLayout>(R.id.price2)
        val _price3 = findViewById<ConstraintLayout>(R.id.price3)
        val _price4 = findViewById<ConstraintLayout>(R.id.price4)
        val _price5 = findViewById<ConstraintLayout>(R.id.price5)

        _price1.setOnClickListener {
            val intent = Intent(this, TicketActivity::class.java)
            startActivity(intent)

        }


    }
}