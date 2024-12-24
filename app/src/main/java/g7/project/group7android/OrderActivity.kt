package g7.project.group7android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

        // Get concert details from intent
        namaKonser = intent.getStringExtra("namaKonser") ?: ""
        deskripsi = intent.getStringExtra("deskripsi") ?: ""
        lokasi = intent.getStringExtra("lokasi") ?: ""
        tanggal = intent.getStringExtra("tanggal") ?: ""
        gambar = intent.getStringExtra("gambar") ?: ""

        // Load concert image
        Picasso.get().load(gambar).into(findViewById<ImageView>(R.id.ivShow))

        // Set seat click listeners
        val seatDetails = listOf(
            "Ultimate Experience: Rp. 15.000.000",
            "My Universe: Rp. 10.000.000",
            "Festival: Rp. 8.000.000",
            "CAT 1: Rp. 5.000.000",
            "CAT 2: Rp. 4.200.000",
            "CAT 3: Rp. 3.000.000"
        )

        // Initially disable the buy ticket button
        buyTicketButton.isEnabled = false

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

//            if (userEmail != null) {
//                firestore.collection("tickets")
//                    .document(userEmail)
//                    .set(ticketData)
//                    .addOnSuccessListener {
//                        val intent = Intent(this, ListTicket::class.java)
//                        startActivity(intent)
//                    }
//                    .addOnFailureListener { e ->
//                        // Handle failure
//                    }
//            }

        }
    }
}