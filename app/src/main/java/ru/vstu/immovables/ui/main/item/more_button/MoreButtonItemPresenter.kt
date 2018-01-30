package ru.vstu.immovables.ui.main.item.more_button

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.PropertiesInfoProvider
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.view.InfoLevel

class MoreButtonItemPresenter(
        private val clicksConsumer: Consumer<Field>
) : ItemPresenter<MoreButtonItemView, Field.MoreButton> {

    override fun bindView(view: MoreButtonItemView, item: Field.MoreButton, position: Int) {
        if (item.more) {
            view.showCloseText()
        } else {
            view.showOpenText()
        }
        view.setClickListener {
            clicksConsumer.accept(item)
        }
    }

}