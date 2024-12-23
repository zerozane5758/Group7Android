package g7.project.group7android


import Konser
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recView: RecyclerView
    private lateinit var konserAdapter: adapterKonser
    private var konserList = mutableListOf<Konser>()

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

        recView = findViewById(R.id.recyclerView)
        recView.layoutManager = GridLayoutManager(this,2)

        fetchKonserData()
        readData()


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


    private fun fetchKonserData() {
        firestore.collection("konser")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val firstDocument = querySnapshot.documents[0]
                    val konserPertama = Konser(
                        firstDocument.getString("namaKonser") ?: "",
                        firstDocument.getString("deskripsi") ?: "",
                        firstDocument.getString("lokasi") ?: "",
                        firstDocument.getString("tanggal") ?: "",
                        firstDocument.get("jenisTiket") as? List<String> ?: emptyList(),
                        firstDocument.getString("gambar") ?: ""
                    )

                    // Set data to the manual view
                    findViewById<TextView>(R.id.tvNama).text = konserPertama.namaKonser
                    findViewById<TextView>(R.id.tvLocation).text = konserPertama.lokasi
                    findViewById<TextView>(R.id.tvDate).text = konserPertama.tanggal
                    Picasso.get().load(konserPertama.gambar).into(findViewById<ImageView>(R.id.ivColdplay))

                    // Set OnClickListener for the manual view
                    findViewById<ImageView>(R.id.ivColdplay).setOnClickListener {
                        val intent = Intent(this, OrderActivity::class.java).apply {
                            putExtra("namaKonser", konserPertama.namaKonser)
                            putExtra("deskripsi", konserPertama.deskripsi)
                            putExtra("lokasi", konserPertama.lokasi)
                            putExtra("tanggal", konserPertama.tanggal)
                            putStringArrayListExtra("jenisTiket", ArrayList(konserPertama.jenisTiket))
                            putExtra("gambar", konserPertama.gambar)
                        }
                        startActivity(intent)
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error fetching data", e)
            }
    }


    private fun readData() {
        firestore.collection("konser")
            .get()
            .addOnSuccessListener { result ->
                konserList.clear()
                for ((index, document) in result.withIndex()) {
                    if (index == 0) continue // Skip the first document

                    val namaKonser = document.getString("namaKonser") ?: ""
                    val deskripsi = document.getString("deskripsi") ?: ""
                    val lokasi = document.getString("lokasi") ?: ""
                    val tanggal = document.getString("tanggal") ?: ""
                    val jenisTiket = document.get("jenisTiket") as? List<String> ?: emptyList()
                    val gambar = document.getString("gambar") ?: ""

                    val readData = Konser(
                        namaKonser,
                        deskripsi,
                        lokasi,
                        tanggal,
                        jenisTiket,
                        gambar
                    )
                    konserList.add(readData)
                }
                konserAdapter = adapterKonser(konserList)
                recView.adapter = konserAdapter
                konserAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.d("Firebase", it.message.toString())
            }
    }


}