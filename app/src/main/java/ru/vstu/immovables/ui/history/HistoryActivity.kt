package ru.vstu.immovables.ui.history

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.vstu.immovables.*
import ru.vstu.immovables.di.ComponentProvider
import ru.vstu.immovables.repository.account.AccountRepository
import ru.vstu.immovables.ui.history.di.HistoryComponent
import ru.vstu.immovables.ui.history.di.HistoryModule
import ru.vstu.immovables.ui.history.list.HistoryListFragment
import ru.vstu.immovables.ui.history.maps.HistoryMapsFragment
import ru.vstu.immovables.ui.login.LoginActivity.Companion.loginScreen
import ru.vstu.immovables.ui.main.MainActivity.Companion.propertiesScreen
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.extractSelectedItem
import ru.vstu.immovables.ui.property_type.PropertyChooseActivity.Companion.propertyChooseScreen
import ru.vstu.immovables.ui.report.ReportActivity.Companion.reportScreen
import javax.inject.Inject


class HistoryActivity : AppCompatActivity(),
        HistoryRouter,
        ComponentProvider<HistoryComponent> {

    @Inject lateinit var propertiesProvider: PropertiesProvider

    @Inject lateinit var accountRepository: AccountRepository

    override lateinit var component: HistoryComponent

    private lateinit var toolbar: Toolbar
    private lateinit var addButton: View

    private lateinit var progress: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = appComponent
                .plus(HistoryModule())
        component.inject(this)
        setContentView(R.layout.activity_history)

        toolbar = findViewById(R.id.toolbar)
        addButton = findViewById(R.id.add_button)
        progress = findViewById(R.id.progress)

        addButton.setOnClickListener { addImmovable() }
        toolbar.title = getString(R.string.History_Title)

        toolbar.inflateMenu(R.menu.history_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    AlertDialog.Builder(this)
                            .setTitle(accountRepository.getLogin())
                            .setMessage(R.string.Dialog_Message)
                            .setPositiveButton(R.string.Dialog_Quit) { dialog, _ ->
                                dialog.cancel()
                                logout()
                            }
                            .setNegativeButton(R.string.Dialog_Cancel) { dialog, _ ->
                                dialog.cancel()
                            }
                            .show()
                    true
                }
                else -> false
            }
        }

        val viewPager: ViewPager = findViewById(R.id.pager)
        viewPager.adapter = ViewPagerAdapter()

        val tabLayout: TabLayout = findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun openReport(reportId: Long) {
        startActivity(reportScreen(reportId))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQ_IMMOVABLES_TYPE) {
            val selectedItem = data?.extractSelectedItem() ?: -1
            startActivity(propertiesScreen(propertiesProvider.types[selectedItem]))
        }
    }

    override fun addImmovable() {
        startActivityForResult(
                propertyChooseScreen(
                        title = getString(R.string.Property_ImmovableType_Title),
                        items = propertiesProvider.types
                ),
                REQ_IMMOVABLES_TYPE
        )
    }

    private fun logout() {
        accountRepository.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress.show() }
                .doOnTerminate { progress.hide() }
                .subscribe({
                    startActivity(loginScreen(intent))
                    finish()
                }, {
                    startActivity(loginScreen(intent))
                    finish()
                })
    }

    internal inner class ViewPagerAdapter : FragmentPagerAdapter(supportFragmentManager) {
        private val fragments = listOf(HistoryListFragment(), HistoryMapsFragment())
        private val fragmentTitles = listOf("Список", "Карта")

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence = fragmentTitles[position]
    }

    companion object {

        fun Context.historyScreen() =
                Intent(this, HistoryActivity::class.java)

    }

}

private val REQ_IMMOVABLES_TYPE = 1