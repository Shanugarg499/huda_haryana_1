package com.example.huda_haryana.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.huda_haryana.R
import com.example.huda_haryana.databinding.CalculatorFragment2Binding
import kotlinx.android.synthetic.main.calculator_fragment2.*
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorFragment : Fragment() {


    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: CalculatorFragment2Binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.calculator_fragment2, container, false)


        binding.apply {
            //Number listeners
            btn00.setOnClickListener { appendOnClick(true, "00") }
            btn0.setOnClickListener { appendOnClick(true, "0") }
            btn1.setOnClickListener { appendOnClick(true, "1") }
            btn2.setOnClickListener { appendOnClick(true, "2") }
            btn3.setOnClickListener { appendOnClick(true, "3") }
            btn4.setOnClickListener { appendOnClick(true, "4") }
            btn5.setOnClickListener { appendOnClick(true, "5") }
            btn6.setOnClickListener { appendOnClick(true, "6") }
            btn7.setOnClickListener { appendOnClick(true, "7") }
            btn8.setOnClickListener { appendOnClick(true, "8") }
            btn9.setOnClickListener { appendOnClick(true, "9") }
            btnDot.setOnClickListener { appendOnClick(true, ".") }


            //Operator Listeners
            btnPlus.setOnClickListener { appendOnClick(false, "+") }
            btnMinus.setOnClickListener { appendOnClick(false, "-") }
            btnMultiply.setOnClickListener { appendOnClick(false, "*") }
            btnDivide.setOnClickListener { appendOnClick(false, "/") }
            btnLeftB.setOnClickListener { appendOnClick(false, "(") }
            btnRightB.setOnClickListener { appendOnClick(false, ")") }
            btnPrecentage.setOnClickListener { appendOnClick(false , "/100") }

            btnClear.setOnClickListener {
                clear()
            }

//            btnEqual.setOnClickListener {
//                calculate()
//            }

        }




        return binding.root
    }


    private fun appendOnClick(clear: Boolean, string: String) {

        binding.tvInput.append(string)
        calculate()

    }

    private fun clear() {
        binding.tvInput.text = ""
        binding.tvOutput.text = ""

    }

    private fun calculate() {

        try {

            val input = ExpressionBuilder(tvInput.text.toString()).build()
            val output = input.evaluate()
            val longOutput = output.toLong()
            if (output == longOutput.toDouble()) {
                binding.tvOutput.text = longOutput.toString()
            } else {
                binding.tvOutput.text = output.toString()
            }

        } catch (e: Exception) {
//            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

}
