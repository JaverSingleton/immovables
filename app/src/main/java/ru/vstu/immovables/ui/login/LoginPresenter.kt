package ru.vstu.immovables.ui.login

import android.os.Bundle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import ru.vstu.immovables.repository.account.AccountRepository

interface LoginPresenter {

    fun attachView(view: LoginView)

    fun detachView()

    fun attachRouter(router: Router)

    fun detachRouter()

    fun onSaveState(): Bundle

    interface Router {

        fun finishLogin()

    }

}

class LoginPresenterImpl(
        private val accountRepository: AccountRepository,
        state: Bundle?
) : LoginPresenter {

    private val disposables = CompositeDisposable()

    private var view: LoginView? = null
    private var router: LoginPresenter.Router? = null

    override fun attachView(view: LoginView) {
        this.view = view
        disposables += view.loginClicks().subscribe {
            accountRepository.login(view.getLogin(), view.getPassword())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { view.showProgress() }
                    .doOnTerminate { view.hideProgress() }
                    .subscribe({
                        router?.finishLogin()
                    }, {
                        view.showError()
                    })
        }
    }

    override fun attachRouter(router: LoginPresenter.Router) {
        this.router = router

        if (accountRepository.isLoggedIn()) {
            router.finishLogin()
        }
    }

    override fun detachRouter() {
        this.router = null
    }

    override fun detachView() {
        disposables.clear()
        this.view = null
    }

    override fun onSaveState(): Bundle = Bundle()

}

