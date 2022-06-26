package una.delta.frontenddelta.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import una.delta.frontenddelta.R
import una.delta.frontenddelta.databinding.ActivityLoginBinding
import una.delta.frontenddelta.model.LoggedInUserView
import una.delta.frontenddelta.model.LoginRequest
import una.delta.frontenddelta.utils.MyApplication.Companion.sessionManager
import una.delta.frontenddelta.utils.SessionManager
import una.delta.frontenddelta.viewmodel.LoginViewModel
import una.delta.frontenddelta.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sessionManager?.fetchAuthToken() != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this,LoginViewModelFactory())[LoginViewModel::class.java]


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            //disable login bottom unless both username/password is valid
            binding.buttonLogin.isEnabled = loginState.isDataValid

            if(loginState.usernameError != null){
                binding.editTextTextPersonName.error = getString(loginState.usernameError)
            }
            if(loginState.passwordError != null){
                binding.editTextTextPassword.error = getString(loginState.passwordError)
            }
        })




        loginViewModel.loginResponse.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            binding.loading.visibility = View.GONE
            if (loginResult.error !=null){
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success !=null){
                updateUiWitUser(loginResult.success)
            }
            setResult(RESULT_OK)
            finish()
        })


        // unico comando parecido que no da error doAfterTextChanged
        binding.editTextTextPersonName.afterTextChanged {
            loginViewModel.loginDataChanged(
                LoginRequest(
                    username = binding.editTextTextPersonName.text.toString(),
                    password = binding.editTextTextPassword.text.toString()
                )
            )
        }


        binding.editTextTextPassword.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    LoginRequest(
                        username = binding.editTextTextPersonName.text.toString(),
                        password = binding.editTextTextPassword.text.toString()
                    )
                )
            }
            setOnEditorActionListener{_,actionId, _ ->
                when(actionId){
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            LoginRequest(
                                username = binding.editTextTextPersonName.text.toString(),
                                password = binding.editTextTextPassword.text.toString()
                            )
                        )
                }
                false
            }

            binding.buttonLogin.setOnClickListener{
                binding.loading.visibility = View.VISIBLE
                loginViewModel.login(
                    LoginRequest(
                        username = binding.editTextTextPersonName.text.toString(),
                        password = binding.editTextTextPassword.text.toString()
                    )
                )
            }

        }

        /*var button_login = findViewById<Button>(R.id.button_login)

    button_login.setOnClickListener{
        val pase_vista = Intent(this, MainActivity::class.java)
        startActivity(pase_vista)
    }*/

    }



    private fun updateUiWitUser(model:LoggedInUserView){
        val welcome = "Welcome! "
        val username = model.username
        finish()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)

        Toast.makeText(
            applicationContext,
            "$welcome $username",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int){
        Toast.makeText(applicationContext,errorString,Toast.LENGTH_SHORT).show()
    }



    fun EditText.afterTextChanged(afterTextChanged:(String)->Unit){
        this.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s:CharSequence,start:Int,count:Int,after:Int) {}
            override fun onTextChanged(s:CharSequence,start:Int,before:Int,after:Int) {}
        })
    }


}