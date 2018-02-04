package ru.vstu.immovables.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.vstu.immovables.R
import ru.vstu.immovables.appComponent
import ru.vstu.immovables.getContainerView
import ru.vstu.immovables.ui.history.HistoryActivity.Companion.historyScreen
import ru.vstu.immovables.ui.login.di.LoginModule
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), LoginPresenter.Router {

    @Inject lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent
                .plus(LoginModule(
                        savedInstanceState
                ))
                .inject(this)
        setContentView(R.layout.activity_login)
        val view: LoginView = LoginViewImpl(
                getContainerView()
        )
        presenter.attachView(view)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachRouter(this)
    }

    override fun onStop() {
        presenter.detachRouter()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun finishLogin() {
        startActivity(intent.getParcelableExtra(KEY_RESULT_INTENT) ?: historyScreen())
        setResult(Activity.RESULT_OK)
        finish()
    }

    companion object {

        fun Context.loginScreen(resultIntent: Intent) =
                Intent(this, LoginActivity::class.java)
                        .putExtra(KEY_RESULT_INTENT, resultIntent)

    }

}

private const val KEY_RESULT_INTENT = "resultIntent"