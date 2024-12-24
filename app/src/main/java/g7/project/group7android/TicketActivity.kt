package g7.project.group7android

import android.content.Intent
import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, ListTicket::class.java)
            startActivity(intent)
        }

        val _eventImage = findViewById<ImageView>(R.id.eventImage)
        val _tvJudul = findViewById<TextView>(R.id.tvJudul)
        val _tvLocation = findViewById<TextView>(R.id.tvLocation)
        val _tvTanggal = findViewById<TextView>(R.id.tvTanggal)
        val _tvType = findViewById<TextView>(R.id.tvType)

        val namaKonser = intent.getStringExtra("namaKonser")
        val lokasi = intent.getStringExtra("lokasi")
        val tanggal = intent.getStringExtra("tanggal")
        val type = intent.getStringExtra("type")
        val gambar = intent.getStringExtra("gambar")

        _tvJudul.text = namaKonser
        _tvLocation.text = lokasi
        _tvTanggal.text = tanggal
        _tvType.text = type
        Picasso.get().load(gambar).into(_eventImage)

    }
}