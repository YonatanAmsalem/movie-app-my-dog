package com.example.matala1
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
var counter = 0
var savedDate:String =""
const val POPCORNPRICE = 15
const val DRINKPRICE =10
const val ADULTPRICE = 5
const val CHILDPRICE = 6
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val button1 = findViewById<Button>(R.id.summary_btn)
        val button2 = findViewById<Button>(R.id.trailer_btn)
        val button3 = findViewById<Button>(R.id.start_order_btn)
        val buttons = listOf(button1, button2, button3)
        animateButtonsWithDelay(buttons, 1000L)
        setupHomePageListeners()
    }
    private fun setupHomePageListeners() {
        val btn_summary = findViewById<Button>(R.id.summary_btn)
        btn_summary.setOnClickListener {
            loadSummaryLayout()
        }
        val btn_trailer = findViewById<Button>(R.id.trailer_btn)
        btn_trailer.setOnClickListener {
            loadTrailerLayout()
        }
        val order_start_btn = findViewById<Button>(R.id.start_order_btn)
        order_start_btn.setOnClickListener {
            loadTicketsOrder()
        }
    }

    private fun loadTrailerLayout() {
        setContentView(R.layout.trailer_movie)
        val getImagev = listOf(
            findViewById<ImageView>(R.id.picOne) to findViewById<TextView>(R.id.myTextView),
            findViewById<ImageView>(R.id.picTwo) to findViewById<TextView>(R.id.myTextView2),
            findViewById<ImageView>(R.id.picThird) to findViewById<TextView>(R.id.myTextView3),
            findViewById<ImageView>(R.id.picfour) to findViewById<TextView>(R.id.myTextView4),
            findViewById<ImageView>(R.id.picfive) to findViewById<TextView>(R.id.myTextView5),
            findViewById<ImageView>(R.id.picsix) to findViewById<TextView>(R.id.myTextView6),
            findViewById<ImageView>(R.id.picseven) to findViewById<TextView>(R.id.myTextView7)
        )
        val shakeimage = listOf( findViewById<ImageView>(R.id.picOne), findViewById<ImageView>(R.id.picTwo),
            findViewById<ImageView>(R.id.picThird),
            findViewById<ImageView>(R.id.picfour),
            findViewById<ImageView>(R.id.picfive) ,
            findViewById<ImageView>(R.id.picsix),
            findViewById<ImageView>(R.id.picseven))

        shakeimage.forEach { imageView ->
            addShakeEffect(imageView)
            }

        getImagev.forEach { (imageView, textView) ->
            setupImageViewToggle(imageView, textView)
        }
        val backToHomeButton = findViewById<Button>(R.id.HomePage)
        backToHomeButton.setOnClickListener {
            setContentView(R.layout.activity_main)
            setupHomePageListeners()
        }
    }

    private fun setupImageViewToggle(imageView: ImageView, textView: TextView) {
        imageView.setOnClickListener {
            if (textView.visibility == View.GONE)
            {
                textView.visibility = View.VISIBLE
                imageView.alpha = 0.4f
            }
            else
            {
                textView.visibility = View.GONE
                imageView.alpha = 1.0f
            }

        }
    }
    private fun loadSummaryLayout() {
        setContentView(R.layout.summary_movie)
        val backToHomeButton = findViewById<Button>(R.id.HomePage)
        backToHomeButton.setOnClickListener {
            setContentView(R.layout.activity_main)
            setupHomePageListeners()
        }
    }

    private fun loadTicketsOrder() {
        setContentView(R.layout.tickers_order)
        val minusbutton = findViewById<Button>(R.id.button_minus)
        val plusbutton = findViewById<Button>(R.id.button_plus)
        val calendertxt = findViewById<TextView>(R.id.Calendertxt)
        val countTxt = findViewById<TextView>(R.id.text_quantity)
        val backToHomeButton = findViewById<Button>(R.id.HomePage)
        val price_show = findViewById<Button>(R.id.showprice)
        val confirmbutton = findViewById<Button>(R.id.Confirmbtn)
        val FirstNameEdit = findViewById<EditText>(R.id.FullNameText)
        val calenderbutton = findViewById<ImageButton>(R.id.calenderbutton)
        val summary_price_txt = findViewById<TextView>(R.id.summary_price)
        var flag = false
        val txt_hour = findViewById<EditText>(R.id.HourText)
        val radioGroupbtn = findViewById<RadioGroup>(R.id.cinema_radio_group)
        val TextAdu = findViewById<EditText>(R.id.adult_option)
        val TextChi = findViewById<EditText>(R.id.child_option)
        val calcaluat_btn = findViewById<Button>(R.id.Calcaluate)
        val popcorn_checkbox = findViewById<CheckBox>(R.id.AddPopcorn)
        val drink_checkbox = findViewById<CheckBox>(R.id.AddDrink)
        val result = resetOrderScreen(FirstNameEdit,TextChi,TextAdu,radioGroupbtn,summary_price_txt,confirmbutton,popcorn_checkbox, drink_checkbox,txt_hour)
        counter = result.first
        flag = result.second
        backToHomeButton.setOnClickListener {
            setContentView(R.layout.activity_main)
            setupHomePageListeners()
        }
        minusbutton.setOnClickListener()
        {
            if (counter > 0) {
                counter--
                countTxt.text = counter.toString()
            } else {
                Toast.makeText(this, getString(R.string.Invalid_tickets_amount), Toast.LENGTH_SHORT).show()
            }
        }
        plusbutton.setOnClickListener()
        {
            if (counter < 50) {
                counter++
                countTxt.text = counter.toString()
            } else {
                Toast.makeText(this, getString(R.string.Over_amount), Toast.LENGTH_SHORT).show()
            }
        }
        calenderbutton.setOnClickListener {
            val c = Calendar.getInstance()
            val currentDateInMillis = c.timeInMillis
            val listenter_t = DatePickerDialog.OnDateSetListener { _, i, i2, i3 ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(i, i2, i3)
                if (selectedCalendar.timeInMillis >= currentDateInMillis) {
                    val selectedDate = "$i3/${i2 + 1}/$i"
                    calendertxt.text = selectedDate
                    savedDate = selectedDate
                    flag = true
                } else {
                    Toast.makeText(this,getString(R.string.date_limit), Toast.LENGTH_SHORT)
                        .show()
                    flag = false
                }
            }
            val dtd = DatePickerDialog(
                this, listenter_t,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            )
            dtd.show()
        }
        var btn_bool = false

        popcorn_checkbox.setOnClickListener({
            if (popcorn_checkbox.isChecked) {
                Toast.makeText(this,getString(R.string.checked_popcorn), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.unchecked_popcorn), Toast.LENGTH_SHORT).show()
            }
        }
        )
        drink_checkbox.setOnClickListener({
            if (drink_checkbox.isChecked) {
                Toast.makeText(this, getString(R.string.checked_drink), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.unchecked_drink), Toast.LENGTH_SHORT).show()
            }
        })
        calcaluat_btn.setOnClickListener({
            val selectedId = radioGroupbtn.checkedRadioButtonId
            if (FullNametxt(FirstNameEdit) == "" || getTime(txt_hour) == null || selectedId == -1 || counter == 0 || (TextAdu.text.toString() == "" && TextChi.text.toString() == "") || savedDate == "") {
                Toast.makeText(
                    this,
                    getString(R.string.calc_checl),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                btn_bool = CheckDate(
                    FirstNameEdit,
                    counter,
                    flag,
                    radioGroupbtn,
                    TextAdu,
                    TextChi,
                    summary_price_txt,
                    popcorn_checkbox,
                    drink_checkbox,
                    txt_hour
                )
                if (btn_bool == true) {
                    confirmbutton.visibility = View.VISIBLE
                }
            }

        })
        confirmbutton.setOnClickListener({
            GetDataFromPage(
                FirstNameEdit,
                TextChi,
                TextAdu,
                radioGroupbtn,
                summary_price_txt,
                popcorn_checkbox,
                drink_checkbox,
                txt_hour
            )
        })

        price_show.setOnClickListener(({
            showPricesDialog()
        }))
    }

    private fun loadsummaryOrder() {
        setContentView(R.layout.summary_order)
        val backToHomeButton = findViewById<Button>(R.id.HomePage)
        backToHomeButton.setOnClickListener {
            setContentView(R.layout.activity_main)
        }
    }

    private fun FullNametxt(fullname: EditText): String? {
        val input = fullname.text.toString().trim()
        if (checkName(input) == false) {
            return null
        } else
        {
            return input;
        }
    }

    private fun checkName(fullname: String): Boolean {
        for (char in fullname) {
            if (char.isLetter() == false && char != ' ')
                return false
        }
        return true;
    }
    private fun getTime(txt_hour: EditText): String? {
        val input = txt_hour.text.toString().trim()
        if (input.length != 5) return null

        val firstchar = input[0].digitToInt()
        val secondchar = input[1].digitToInt()
        val thirdchar = input[2]
        val fourthchar = input[3].digitToInt()
        val lastchar = input[4].digitToInt()

        val hours = firstchar * 10 + secondchar
        val minutes = fourthchar * 10 + lastchar

        if (hours !in 10..23) return null
        if (thirdchar != ':' && thirdchar != '.') return null
        if (minutes % 15 != 0 || minutes > 60) return null

        return input
    }

    private fun SelectRadio(radioGroupbtn: RadioGroup) {
        val selectedId = radioGroupbtn.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, getString(R.string.cinema_check), Toast.LENGTH_SHORT)
                .show()
        }
    }
    private fun GetCounter(adutext: EditText, childtext: EditText): Pair<Int, Int> {
        var x = 0
        var y = 0
        if(adutext.text.toString().isNotEmpty())
        {
            x = adutext.text.toString().toInt()
        }
        if(childtext.text.toString().isNotEmpty())
        {
            y = childtext.text.toString().toInt()
        }
        else
        {
                x = 0
                y = 0

        }
        if (counter < x + y) {
            Toast.makeText(this, getString(R.string.amount_by_choose), Toast.LENGTH_SHORT).show()
            return Pair(0, 0)
        } else {
            return Pair(x, y)
        }
    }
    fun calcaluatePrice(resulte: Pair<Int, Int>,check_popcorn: CheckBox,check_drink: CheckBox): Int {

        var flagpopcorn = 0
        var flagdrink = 0
        if (counter < resulte.first + resulte.second || (resulte.first == 0 && resulte.second == 0)) {
            Toast.makeText(this, getString(R.string.amount_by_choose) ,Toast.LENGTH_SHORT).show()
            return 0
        } else {
            if(check_popcorn.isChecked)
            {
                flagpopcorn =1
            }
            if(check_drink.isChecked)
            {
                flagdrink = 1
            }
            return ((ADULTPRICE * resulte.first) + (CHILDPRICE * resulte.second) +
                    (resulte.first * flagpopcorn * POPCORNPRICE) + (resulte.second * flagpopcorn * POPCORNPRICE)
                    +(resulte.first * flagdrink * DRINKPRICE) + (resulte.second * flagdrink * DRINKPRICE))
        }
    }
    private fun CheckDate(
        firstNameEdit: EditText,
        counter: Int,
        flag: Boolean,
        radioGroup: RadioGroup,
        textAdu: EditText,
        textChi: EditText,
        summaryTextView: TextView,
        check_popcorn:CheckBox,
        check_drink:CheckBox,
        txt_hour: EditText
    ): Boolean {
        var checkB = true
        var tempStr = FullNametxt(firstNameEdit)
        var hourstr = getTime(txt_hour)
        if (tempStr == null) {
            Toast.makeText(this, getString(R.string.full_name_check), Toast.LENGTH_SHORT).show()
            checkB = false
        } else {
            checkB = true
        }
        if (hourstr == null) {
            Toast.makeText(this, getString(R.string.full_name_check), Toast.LENGTH_SHORT).show()
            checkB = false
        } else {
            checkB = true
        }
        if (counter == 0) {
            Toast.makeText(this, getString(R.string.ticketsamount_check), Toast.LENGTH_SHORT).show()
            checkB = false
        }
        if (flag == false) {
            Toast.makeText(this, getString(R.string.date_check), Toast.LENGTH_SHORT).show()
            checkB = false
        }
        SelectRadio(radioGroup)
        val result = GetCounter(textAdu, textChi)
        if (result.first == 0 && result.second == 0) {
            Toast.makeText(
                this,
                getString(R.string.div_check),
                Toast.LENGTH_SHORT
            ).show()
            checkB = false
        } else {
            val price = calcaluatePrice(result,check_popcorn,check_drink)
            summaryTextView.text = "$price NIS"
        }
        return checkB
    }

    private fun resetOrderScreen(
        firstNameEdit: EditText,
        childTicketsEdit: EditText,
        adultTicketsEdit: EditText,
        radioGroup: RadioGroup,
        summaryTextView: TextView,
        confirmbutton: Button,
        popcorn_check:CheckBox,
        drink_check : CheckBox,
        txt_hour: EditText
    ): Pair<Int, Boolean> {
        firstNameEdit.text.clear()
        childTicketsEdit.text.clear()
        adultTicketsEdit.text.clear()
        txt_hour.text.clear()
        radioGroup.clearCheck()
        summaryTextView.text = getString(R.string.price_total)
        popcorn_check.isChecked = false
        drink_check.isChecked = false
        confirmbutton.visibility = View.GONE
        return Pair(0, false)
    }
    private fun GetDataFromPage(
        firstNameEdit: EditText,
        childTicketsEdit: EditText,
        adultTicketsEdit: EditText,
        radioGroup: RadioGroup,
        summaryTextView: TextView,
        popcorn_check: CheckBox,
        drink_check: CheckBox,
        txt_hour: EditText
    ) {
        setContentView(R.layout.summary_order)
        val firstnametext = findViewById<TextView>(R.id.getFullName)
        val TicketsNumber = findViewById<TextView>(R.id.getTickets)
        val DataNum = findViewById<TextView>(R.id.getDate)
        val Back_Btn = findViewById<Button>(R.id.back_of_btn)
        val Divide = findViewById<TextView>(R.id.getAge)
        val additiontxt = findViewById<TextView>(R.id.getAddition)
        val price = findViewById<TextView>(R.id.getPrice)
        val txt_r = findViewById<TextView>(R.id.getTheatre)
        val statuspurchase = findViewById<TextView>(R.id.Status_purchase)
        val confirmbtn = findViewById<Button>(R.id.confirm_btn)
        val homebtn = findViewById<Button>(R.id.HomePage)
        val selectedId = radioGroup.checkedRadioButtonId
        val timetxt = findViewById<TextView>(R.id.getTime)
        resetSummaryOrder(
            firstnametext,
            TicketsNumber,
            DataNum,
            Divide,
            price,
            statuspurchase,
            txt_r,
            additiontxt,
            timetxt
        )
        firstnametext.text = firstNameEdit.text
        TicketsNumber.text = counter.toString()
        DataNum.text = savedDate
        timetxt.text=txt_hour.text
        when (selectedId) {
            R.id.Tel_aviv -> txt_r.text =getString(R.string.oneChoice)
            R.id.Haifa -> txt_r.text = getString(R.string.secoundchoice)
            R.id.Eilat -> txt_r.text = getString(R.string.threecohice)
        }
        val adultCount = adultTicketsEdit.text.toString().toIntOrNull() ?: 0
        val childCount = childTicketsEdit.text.toString().toIntOrNull() ?: 0
        Divide.text = getString(R.string.adult_radio) + ": " + adultCount + " " +
                getString(R.string.child_button) + ": " + childCount
        when{
            popcorn_check.isChecked == true && drink_check.isChecked == false -> additiontxt.text = getString(R.string.popcorn)
            drink_check.isChecked == true && popcorn_check.isChecked == false ->additiontxt.text =getString(R.string.drinks)
            drink_check.isChecked == true&& popcorn_check.isChecked == true -> additiontxt.text =getString(R.string.combineadds)
        }
        price.text = summaryTextView.text.toString()
        confirmbtn.setOnClickListener(
            {
                showStatusPurchase()
                Handler().postDelayed({
                    changeBackground()
                },3500)
                    Handler().postDelayed({
                    setContentView(R.layout.activity_main)
                    setupHomePageListeners()
                },8000)
            })
        Back_Btn.setOnClickListener({
            loadTicketsOrder()
        }
        )
        homebtn.setOnClickListener({
            setContentView(R.layout.activity_main)
            setupHomePageListeners()
        })
    }


    private fun showStatusPurchase() {
        findViewById<TextView>(R.id.Status_purchase).visibility = View.VISIBLE
        Toast.makeText(this, getString(R.string.finalorder), Toast.LENGTH_SHORT).show()
    }

    private fun changeBackground() {
        val linearchange = findViewById<LinearLayout>(R.id.LayoutSummary)
        linearchange.setBackgroundResource(R.drawable.confirmimg)

        val lineartranspancy = findViewById<LinearLayout>(R.id.layourfortranspancy)
        lineartranspancy.alpha = 0.1f
    }

    private fun resetSummaryOrder(
        firstnametext: TextView,
        TicketsNumber: TextView,
        DataNum: TextView,
        Divide: TextView,
        price: TextView,
        statuspurchase: TextView,
        txt_r: TextView,
        additiontxt: TextView,
        timetxt: TextView
    ) {
        firstnametext.text = ""
        TicketsNumber.text = ""
        DataNum.text = ""
        Divide.text = ""
        timetxt.text=""
        additiontxt.text = getString(R.string.empty)
        price.text = getString(R.string.price_total)
        DataNum.text = ""
        statuspurchase.visibility = View.GONE
        txt_r.text = ""
    }
    private fun animateButtonsWithDelay(buttons: List<View>, delay: Long) {
        for ((index, button) in buttons.withIndex()) {
            button.alpha = 0f
            button.postDelayed({
                val animator = ObjectAnimator.ofFloat(button, "alpha", 0f, 1f)
                animator.duration = 500
                animator.start()
            }, index * delay)
        }
    }
    private fun showPricesDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(getString(R.string.pmovie))
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.imgprice)
        imageView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        imageView.adjustViewBounds = true
        dialogBuilder.setView(imageView)
        dialogBuilder.setPositiveButton(getString(R.string.closewindow)) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }
        private fun addShakeEffect(imageView: ImageView, infinite: Boolean = true) {
            val animator = ObjectAnimator.ofFloat(imageView, "translationX", 0f, 10f, -10f, 10f, -10f, 0f)
            animator.duration = 250
            animator.repeatCount = 15
            animator.start()
        }
}
