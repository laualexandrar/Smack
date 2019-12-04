package com.lroap.smack.Controller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lroap.smack.R
import com.lroap.smack.Services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {


    var userAvatar = "profileDefault" // aqui si la persona no quiere elegir ninguna imagen esta se queda
    var avatarColor = "[0.5, 0.5, 0.5, 1]" // este es el default para el color, los 0.5 significan red, green, blue y el uno es el alfa


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar (view: View){
        val random = Random()
        val color = random.nextInt(2)//va a generar random el siguiente int, al poner dos me genera numeros entre el cero y el uno
        val avatar = random.nextInt(28)// porque nuestras imagenes van de 0 a 27

        if(color == 0){
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar,"drawable", packageName) // aqui creo una var llamada resourceId y de resources (res)
        // traigo el identificador que serian los nombres de las imagenes que tengo en el drawable
        createAvatarImageView.setImageResource(resourceId) // generamos una imagen random

    }

    fun generateColorClicked (view: View){
        val random = Random() //r g b significa red green blue
        val r = random.nextInt(255) // el color que necesitamos esta en android entre cero y 255
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble()/ 255 //, estoy pasando un int a double, es decir que entre cero y uno me esta buscando un numero hasta 255
        val savedG = g.toDouble()/ 255
        val savedB = b.toDouble()/ 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]" // aqui llamo avatarColor de arriba solo que paso mis nuevas variables
       // println(avatarColor) //esto me sirve para ver en el log cat el numero
    }

    fun createUserClicked(view: View){
        AuthService.registerUser(this,"j@j.com", "123456"){ complete ->

            if(complete){

            }
        }
    }
}
