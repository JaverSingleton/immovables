package ru.vstu.immovables.ui.choose_from_list

class ChoosePresenterImpl(private val view: ChooseView): ChoosePresenter {

    private var elementId: Long = 0

    override fun onCreate(elementId: Long?, data: Array<String>?) {
        this.elementId = elementId ?: 0L

        if(data == null || data.isEmpty()){
            view.setResultCancel(this.elementId)
        } else {
            view.showData(data)
        }
    }

    override fun onClick(position: Int) {
        view.setResultSuccess(elementId, position)
    }

    override fun onDestroy() {

    }
}