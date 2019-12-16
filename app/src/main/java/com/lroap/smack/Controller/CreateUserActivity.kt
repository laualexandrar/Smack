package com.lroap.smack.Controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.lroap.smack.R
import com.lroap.smack.Services.AuthService
import com.lroap.smack.Services.UserDataService
import com.lroap.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {


    var userAvatar = "profileDefault" // aqui si la persona no quiere elegir ninguna imagen esta se queda
    var avatarColor = "[0.5, 0.5, 0.5, 1]" // este es el default para el color, los 0.5 significan red, green, blue y el uno es el alfa


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
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

        enableSpinner(true)

        val userName = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
            AuthService.registerUser(this, email , password){ registerSuccess ->
                if(registerSuccess){
                    AuthService.loginUser(this, email, password){ loginSuccess ->
                        if(loginSuccess){
                            AuthService.createUser(this, userName, email, userAvatar, avatarColor){ createSuccess ->
                                if(createSuccess){

                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
//                                println(UserDataService.avatarName)
//                                println(UserDataService.avatarColor)
//                                println(UserDataService.name)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }
//                        println(AuthService.authToken)
//                        println(AuthService.userEmail)
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        } else {
            Toast.makeText(this, "Make sure user name, email, and password are filled in.",
                Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }

    }

    fun errorToast(){
        Toast.makeText(this, "Somehing went wrong, please try again",
            Toast.LENGTH_LONG).show()
            enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean){
        if (enable){
            createSpinner.visibility = View.VISIBLE

        } else {
            createSpinner.visibility = View.INVISIBLE

        }
        createUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backGroundColorBtn.isEnabled = !enable
    }
}
