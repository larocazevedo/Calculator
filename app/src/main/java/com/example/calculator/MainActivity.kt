package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    // criando uma entrada nula para achar o texto onde será exibido o resultado dos cálculos
    private var tvInput: TextView? = null
    // variáveis que irão tratar o ponto da calculadora, transformar em decimal e não permitir que
    // o . apareça 2 ou mais vezes
    var lastNumeric : Boolean = false  // toda operação precisa do último número para ser realizada
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //localizando a variável pelo ID (assim podemos ter uma visualização de texto)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text) //quando a view é um botão quero obter seu texto
        lastNumeric = true
        lastDot = false
    }

    // função criada para o botão CLR para limpar a informação exibida na tela
    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    // Este método será chamado assim que algum dos operadores (/ * - +) for clicado
    fun onOperator(view: View) {
        // verificar se o texto inserido está vazio ou não tvInput?.text?
        tvInput?.text?.let {
            // SE NÃO ESTIVER VAZIO, seguirá as instruções a seguir
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                // anexar qualquer que seja o botão que tenha um texto ao texto existente
                // para inserir
                tvInput?.append((view as Button).text)
                // depois certificar que o último numérico e último ponto esteja definido como falso
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString() //pega a entrada da visualização de texto e nos
            //fornece um text, em seguida cria uma string pq quero dar um valor para ser uma string
            // real e não ser text (pq text é apenas um charsequence)
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }

    // Esse método irá checar se existem os sinais / * - + e retornar true ou false
    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}