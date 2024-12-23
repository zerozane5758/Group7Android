package g7.project.group7android


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        fetchKonserData()


        findViewById<ImageView>(R.id.ivLogout).setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val _tvNama = findViewById<TextView>(R.id.tvNama)
        _tvNama.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        val _logout = findViewById<ImageView>(R.id.ivLogout)
        _logout.setOnClickListener() {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
    fun updateUI(nama: String, gambar: String, lokasi: String, tanggal: String) {
        findViewById<TextView>(R.id.tvNama).text = nama
        findViewById<TextView>(R.id.tvLocation).text = lokasi
        findViewById<TextView>(R.id.tvDate).text = tanggal
    }

    fun fetchKonserData() {
        firestore.collection("konser")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val nama = document.getString("namaKonser") ?: ""
                    val gambar = document.getString("gambar") ?: ""
                    val lokasi = document.getString("lokasi") ?: ""
                    val tanggal = document.getString("tanggal") ?: ""
                    val imageView = findViewById<ImageView>(R.id.ivColdplay)
                    Picasso.get()
                        .load(gambar)
                        .into(imageView)


                    // Update UI
                    updateUI(nama, gambar, lokasi, tanggal)
                    break // Jika hanya ingin menampilkan satu konser, hentikan setelah mengambil yang pertama
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal mengambil data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }



}