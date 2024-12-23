package g7.project.group7android

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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