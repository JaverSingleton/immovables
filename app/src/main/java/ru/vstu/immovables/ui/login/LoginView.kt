package ru.vstu.immovables.ui.login

import android.support.v7.widget.Toolbar
import android.text.InputType
import android.view.View
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import ru.vstu.immovables.R
import ru.vstu.immovables.hide
import ru.vstu.immovables.show
import ru.vstu.immovables.showToast
import ru.vstu.immovables.ui.view.TextInputView

interface LoginView {

    fun loginClicks(): Observable<Unit>

    fun navigationClicks(): Observable<Unit>

    fun getLogin(): String

    fun getPassword(): String

    fun showProgress()

    fun hideProgress()

    fun showError()

}

class LoginViewImpl(view: View) : LoginView {

    private val context = view.context

    private val toolbar: Toolbar = view.findViewById(R.id.toolbar)

    private val loginButton: View = view.findViewById(R.id.login_button)

    private val loginView: TextInputView = view.findViewById(R.id.login)
    private val passwordView: TextInputView = view.findViewById(R.id.password)

    private val progress: View = view.findViewById(R.id.progress)

    init {
        loginView.hint = context.getString(R.string.Login_Login_Hint)
        loginView.maxLines = 1
        passwordView.hint = context.getString(R.string.Login_Password_Hint)
        passwordView.maxLines = 1
        passwordView.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
    }

    override fun loginClicks(): Observable<Unit> = loginButton.clicks()

    override fun navigationClicks(): Observable<Unit> = toolbar.clicks()

    override fun getLogin(): String = loginView.text.toString()

    override fun getPassword(): String = passwordView.text.toString()

    override fun showProgress() {
        progress.show()
    }

    override fun hideProgress() {
        progress.hide()
    }

    override fun showError() {
        context.showToast(R.string.Login_Error)
    }

}