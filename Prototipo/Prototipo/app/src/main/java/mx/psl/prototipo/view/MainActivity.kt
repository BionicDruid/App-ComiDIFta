package mx.psl.prototipo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.psl.prototipo.R
//import android.content.Context

/**
 * @author: Pablo Spínola López
 *
 * Main activity, aquí se inicializa la pantalla inicial
 */

class MainActivity : AppCompatActivity() {

    //val sharedPref = this.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.menuAdministrador)


        if (currentFragment is MenuAdmin) {
            // Ignore back press
        } else {
            super.onBackPressed()
        }
    }

}