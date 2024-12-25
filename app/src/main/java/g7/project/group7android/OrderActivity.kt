package g7.project.group7android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class OrderActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var buyTicketButton: Button
    private lateinit var seatLayouts: List<View>
    private lateinit var namaKonser: String
    private lateinit var deskripsi: String
    private lateinit var lokasi: String
    private lateinit var tanggal: String
    private lateinit var gambar: String
    private var selectedSeat: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Initialize views
        buyTicketButton = findViewById(R.id.buyTicket)
        seatLayouts = listOf(
            findViewById(R.id.price1),
            findViewById(R.id.price2),
            findViewById(R.id.price3),
            findViewById(R.id.price4),
            findViewById(R.id.price5),
            findViewById(R.id.price6)
        )

        val titleIds = listOf(
            R.id.title1, R.id.title2, R.id.title3,
            R.id.title4, R.id.title5, R.id.title6
        )
        val hargaIds = listOf(
            R.id.harga1, R.id.harga2, R.id.harga3,
            R.id.harga4, R.id.harga5, R.id.harga6
        )

        // Get concert details from intent
        namaKonser = intent.getStringExtra("namaKonser") ?: ""
        deskripsi = intent.getStringExtra("deskripsi") ?: ""
        lokasi = intent.getStringExtra("lokasi") ?: ""
        tanggal = intent.getStringExtra("tanggal") ?: ""
        val jenisTiket = intent.getStringArrayListExtra("jenisTiket") ?: arrayListOf()
        val hargaTiket = intent.getStringArrayListExtra("hargaTiket") ?: arrayListOf()
        gambar = intent.getStringExtra("gambar") ?: ""

        val _tvTitle = findViewById<TextView>(R.id.tvTitle)
        val _tvDescription2 = findViewById<TextView>(R.id.tvDescription2)
        val _tvLocation = findViewById<TextView>(R.id.tvLocation)
        val _tvDate = findViewById<TextView>(R.id.tvDate)

        _tvTitle.text = namaKonser
        _tvDescription2.text = deskripsi
        _tvLocation.text = lokasi
        _tvDate.text = tanggal
        // Load concert image
        Picasso.get().load(gambar).into(findViewById<ImageView>(R.id.ivShow))

        // Populate seat details dynamically
        val seatDetails = mutableListOf<String>()
        for (i in 0 until titleIds.size) {
            val titleView = findViewById<TextView>(titleIds[i])
            val hargaView = findViewById<TextView>(hargaIds[i])

            if (i < jenisTiket.size && i < hargaTiket.size) {
                titleView.text = jenisTiket[i]
                hargaView.text = hargaTiket[i]
                seatDetails.add("${jenisTiket[i]}: ${hargaTiket[i]}")
                seatLayouts[i].visibility = View.VISIBLE
            } else {
                titleView.text = ""
                hargaView.text = ""
                seatLayouts[i].visibility = View.GONE
            }
        }

        // Initially disable the buy ticket button
        buyTicketButton.isEnabled = false

        // Set seat click listeners
        seatLayouts.forEachIndexed { index, layout ->
            layout.setOnClickListener {
                selectedSeat = seatDetails[index]
                buyTicketButton.isEnabled = true
                buyTicketButton.text = "Buy Ticket: ${seatDetails[index].split(": ")[1]}"
                seatLayouts.forEach { it.alpha = 1.0f }
                layout.alpha = 0.5f
            }
        }

        // Set buy ticket button click listener
        buyTicketButton.setOnClickListener {
            val userEmail = firebaseAuth.currentUser?.email
            val seatName = selectedSeat?.split(": ")?.get(0) // Get seat name without price
            val ticketData = hashMapOf(
                "namaKonser" to namaKonser,
                "deskripsi" to deskripsi,
                "lokasi" to lokasi,
                "tanggal" to tanggal,
                "gambar" to gambar,
                "seat" to seatName,
                "price" to selectedSeat?.split(": ")?.get(1),
                "email" to userEmail
            )

            if (userEmail != null) {
                firestore.collection("tickets")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val maxId = querySnapshot.documents.mapNotNull { it.id.toIntOrNull() }.maxOrNull() ?: 0
                        val newId = (maxId + 1).toString()

                        firestore.collection("tickets")
                            .document(newId)
                            .set(ticketData)
                            .addOnSuccessListener {
                                val intent = Intent(this, ListTicket::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                // Handle failure
                            }
                    }
                    .addOnFailureListener { e ->
                        // Handle failure
                    }
            }
        }
    }
}
