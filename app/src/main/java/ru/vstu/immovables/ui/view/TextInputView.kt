package ru.vstu.immovables.ui.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StyleRes
import android.support.v7.view.ContextThemeWrapper
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.AbsSavedState
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.EditText
import android.widget.FrameLayout
import ru.vstu.immovables.*
import kotlin.properties.Delegates

internal val ANIMATION_DURATION: Long = 200

internal val COLLAPSED_FRACTION: Float = 0.0f
internal val EXPANDED_FRACTION: Float = 1.0f

class TextInputView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var currentEditText: EditText by Delegates.notNull()

    private val textPainter = TextPainter(this)
    private val animator = ValueAnimator().apply {
        interpolator = AccelerateInterpolator()
        duration = ANIMATION_DURATION
        addUpdateListener({ textPainter.fraction = it.animatedValue as Float })
    }

    var textChangeListener: TextWatcher? = null
        get() = field
        set(value) {
            currentEditText.removeTextChangedListener(field)
            field = value
            currentEditText.addTextChangedListener(value)
        }

    var focusChangeListener: ((hasFocus: Boolean) -> Unit)? = null

    var textLength: Int = Int.MAX_VALUE
        get() = field
        set(value) {
            field = value
            currentEditText.filters = arrayOf(InputFilter.LengthFilter(value))
        }

    var enable: Boolean
        get() = currentEditText.isEnabled
        set(value) {
            currentEditText.isEnabled = value
        }

    var text: CharSequence
        get() = currentEditText.text
        set(value) {
            currentEditText.setText(value)
            resetTextFraction()
        }

    private fun resetTextFraction() {
        textPainter.fraction = if (currentEditText.text.isNotEmpty() || hasFocus()) {
            COLLAPSED_FRACTION
        } else {
            EXPANDED_FRACTION
        }
    }

    var hint: CharSequence
        get() = textPainter.text
        set(value) {
            textPainter.text = value
        }

    var inputType: Int
        get() = currentEditText.inputType
        set(value) {
            currentEditText.inputType = value
        }

    var imeOptions: Int
        get() = currentEditText.imeOptions
        set(value) {
            currentEditText.imeOptions = value
        }

    var hasError: Boolean by Delegates.observable(false) { _, old, new ->
        if (new != old) {
            textPainter.hasError = new
            replaceEditText()
        }
    }

    var maxLines: Int = Int.MAX_VALUE
        get() = field
        set(value) {
            field = value
            currentEditText.bindMaxLines(value)
        }

    fun setHintResId(stringId: Int) {
        textPainter.text = context.getString(stringId)
    }

    fun resetText() {
        currentEditText.setText(text)
    }

    private val normalStyle = R.style.TextInputAppearance
    private val errorStyle = R.style.TextInputAppearanceError

    init {
        //By default LinearLayout doesn't call onDraw() method
        setWillNotDraw(false)
        setEditView(createStyledEditText(normalStyle))
        initPaddings()
    }

    private fun initPaddings() {
        with(resources) {
            minimumHeight = getDimensionPixelSize(R.dimen.text_input_min_height)
            val horizontalPadding = getDimensionPixelSize(R.dimen.text_input_big_padding)
            val verticalPadding = getDimensionPixelSize(R.dimen.text_input_small_padding)
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val parcel = super.onSaveInstanceState()
        val state = SavedState(
                hasError = hasError,
                editState = currentEditText.onSaveInstanceState(),
                superState = parcel)
        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            hasError = state.hasError
            currentEditText.onRestoreInstanceState(state.editState)
            resetTextFraction()
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    class SavedState : AbsSavedState {
        val hasError: Boolean
        val editState: Parcelable

        constructor(parcel: Parcel) : super(parcel) {
            hasError = parcel.readBoolean()
            editState = parcel.readParcelable()
        }

        constructor(
                hasError: Boolean,
                editState: Parcelable,
                superState: Parcelable
        ) : super(superState) {
            this.hasError = hasError
            this.editState = editState
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            super.writeToParcel(dest, flags)

            dest?.apply {
                writeBoolean(hasError)
                writeParcelable(editState, flags)
            }
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> =
                    Parcels.creator {
                        SavedState(this)
                    }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        textPainter.height = height
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        textPainter.draw(canvas)
    }

    private fun collapseHint() {
        animateFraction(COLLAPSED_FRACTION)
    }

    private fun expandHint() {
        animateFraction(EXPANDED_FRACTION)
    }

    private fun animateFraction(target: Float) {
        if (textPainter.fraction == target) {
            return
        }
        if (animator.isRunning) {
            animator.cancel()
        }
        //Hack to show a proper color at the beginning of the collapse animation
        val startFraction = with(textPainter) { if (fraction == 0f) fraction + 1f / ANIMATION_DURATION else fraction }
        animator.setFloatValues(startFraction, target)
        animator.start()
    }

    /**
     * Remove old view only after adding new one to keep focus inside
     */
    private fun replaceEditText() {
        val newView = currentEditText.clone(if (hasError) errorStyle else normalStyle)
        val oldView = currentEditText
        val hasFocus = oldView.hasFocus()
        setEditView(newView)
        if (hasFocus) {
            newView.showKeyboard()
        }
        removeView(oldView)
    }

    private fun setEditView(editText: EditText) {
        currentEditText = editText
        addView(currentEditText)
    }

    private fun EditText.clone(@StyleRes style: Int): EditText {
        this.onFocusChangeListener = null
        val newEditText = createStyledEditText(style)
        newEditText.imeOptions = this.imeOptions
        newEditText.inputType = this.inputType
        newEditText.onRestoreInstanceState(this.onSaveInstanceState())
        if (this@TextInputView.maxLines < Int.MAX_VALUE) newEditText.bindMaxLines(this@TextInputView.maxLines)
        if (textLength < Int.MAX_VALUE) newEditText.filters = arrayOf(InputFilter.LengthFilter(textLength))
        if (textChangeListener != null) newEditText.addTextChangedListener(textChangeListener)
        return newEditText
    }

    /**
     * The only good way to change a color of a cursor inside an editText is to replace the editText with the new one
     * styled with another color.
     */
    @SuppressLint("RestrictedApi")
    private fun createStyledEditText(@StyleRes style: Int): EditText {
        val editText = EditText(ContextThemeWrapper(context, style))
        editText.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
            topMargin = resources.getDimensionPixelSize(R.dimen.text_input_top_padding)
        }
        editText.setBackgroundCompat(ColorDrawable(Color.TRANSPARENT))
        editText.changePadding(0, 0, 0, 0)
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.text_input_text_size).toFloat())
        editText.setOnFocusChangeListener { view, hasFocus ->
            focusChangeListener?.invoke(hasFocus)
            animateHintOnFocusChange(view, hasFocus)
        }
        return editText
    }

    private fun animateHintOnFocusChange(view: View, hasFocus: Boolean) {
        val hasText = TextUtils.isEmpty((view as EditText).text).not()
        textPainter.hasFocus = hasFocus
        if (hasText || hasFocus) {
            collapseHint()
        } else {
            expandHint()
        }
    }

    /**
     * To show ripple even if event landed on editText
     */
    override fun onInterceptTouchEvent(event: MotionEvent) = true

    override fun onTouchEvent(event: MotionEvent): Boolean {
        updateBackgroundState(event)
        if (propagateTouchEventToEditText(event)) return true
        else return super.onTouchEvent(event)
    }

    private fun updateBackgroundState(event: MotionEvent) {
        background ?: return
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                background.setHotspotCompat(event.x, event.y)
                background.state = createDrawableState(isPressed = true)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                background.state = createDrawableState(isPressed = false)
            }
        }
    }

    private fun createDrawableState(isPressed: Boolean) = intArrayOf(
            android.R.attr.state_enabled.negateIfFalse(isEnabled),
            android.R.attr.state_focused.negateIfFalse(isFocused),
            android.R.attr.state_pressed.negateIfFalse(isPressed)
    )

    private fun Int.negateIfFalse(condition: Boolean): Int = if (condition) this else -this

    /**
     * Make sure that editText will get focus even if event landed outside it's borders
     */
    @SuppressLint("Recycle")
    private fun propagateTouchEventToEditText(event: MotionEvent): Boolean {
        val coercedX = (event.x - currentEditText.left).coerceAtMost(currentEditText.width.toFloat())
        val coercedY = (event.y - currentEditText.top).coerceAtMost(currentEditText.height.toFloat())
        val updatedEvent = MotionEvent.obtain(event).apply {
            setLocation(coercedX, coercedY)
        }
        return currentEditText.onTouchEvent(updatedEvent)
    }

    private fun EditText.bindMaxLines(maxLines: Int) {
        this.maxLines = maxLines
        this.setSingleLine(maxLines == 1)
    }
}