package ru.vstu.immovables.ui.main.item.photo

import com.avito.konveyor.blueprint.ItemPresenter
import io.reactivex.functions.Consumer
import ru.vstu.immovables.ui.main.PropertiesInfoProvider
import ru.vstu.immovables.ui.main.item.Field
import ru.vstu.immovables.ui.view.InfoLevel

class PhotoItemPresenter(
        private val propertiesInfoProvider: PropertiesInfoProvider,
        private val clicksConsumer: Consumer<Field>,
        private val valueChangesConsumer: Consumer<Field>
) : ItemPresenter<PhotoItemView, Field.Photo> {
    override fun bindView(view: PhotoItemView, item: Field.Photo, position: Int) {
        view.setValue(item.title, item.photos.size, item.maxSelectable)
        view.setClickListener { clicksConsumer.accept(item) }
        view.setClearClickListener {
            item.photos = listOf()
            view.setValue(item.title, item.photos.size, item.maxSelectable)
            valueChangesConsumer.accept(item)
        }
        view.setInfo(
                info = item.info?.text
                        ?: item.isMandatory
                        .takeIf { it && propertiesInfoProvider.more }
                        ?.let { "Обязательное поле" },
                level = item.info?.level
                        ?: item.isMandatory
                        .takeIf { it && propertiesInfoProvider.more }
                        ?.let { InfoLevel.WARNING } ?: InfoLevel.INFO
        )
    }
}