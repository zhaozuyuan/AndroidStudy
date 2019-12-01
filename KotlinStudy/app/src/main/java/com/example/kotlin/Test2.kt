package com.example.kotlin

import kotlin.properties.Delegates

/**
 * create by zuyuan on 2019/11/16
 */
class Test2 {

    val v: Int by lazy { 3 }

    companion object {
        var name: String = ""

        fun getSimpleName() = name
    }

    val name: String by lazy { "" }

    var i: String by Delegates.observable("", { property, oldValue, newValue ->
        println("${property.isLateinit}--$oldValue--$newValue")
    })

    var i2: String by Delegates.vetoable("", {property, oldValue, newValue ->
        false
    })

}
class UserX(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}


data class T(var i: Int)
fun main() {
    fun stepNext(v: Int) {
        println("stepNext中的值: $v")
    }

    val t = T(0)
    for (i: Int in 0..10) {
        val selector = i
        t.i = selector
        println("i的值是: $selector")
        stepNext(selector)
    }
}

inline fun <reified T> test(sum: () -> T) : T {
    val t1 = T::class.java.newInstance()
    val t2 = sum()

    return if (t1 == t2) t1 else t2
}

/*表达式*/
/*存在问题，每次必须手动去判断是哪一个类类型*/
//sealed class Expr {
//
//    /*整数*/
//    data class Num(val value: Int) : Expr()
//
//    /*操作 opName:操作符 */
//    data class Operate(val opName: String, val left: Expr, val right: Expr) : Expr()
//
//    /*直接得出简单的表达式结果 0+n=n;n+0=n*/
//    /*此时存在了大量的判断逻辑，并耦合在if else中*/
//    fun simplifyExpr1(expr: Expr) : Expr = if (expr is Num) {
//        expr
//    } else if (expr is Operate && expr.opName == "+" && expr.left is Num
//        && expr.right is Num && expr.left.value == 0) {
//        expr.right
//    } else if (expr is Operate && expr.opName == "+" && expr.left is Num
//        && expr.right is Num && expr.right.value == 0) {
//        expr.right
//    } else {
//        expr
//    }
//
//    /*使用when结构，进行递归访问，使得判断逻辑简洁明了*/
//    /*但是在嵌套结构很深的情况下，该方法不一定适合*/
//    fun simlifyExpr2(expr: Expr) : Expr = when(expr) {
//        is Num -> expr
//        is Operate -> when(expr) {
//            //n+0=n
//            Operate("+", expr.left, Num(0)) -> simlifyExpr2(expr.left)
//            //0+n=n
//            Operate("+", Num(0), expr.right) -> simlifyExpr2(expr.right)
//            else -> expr
//        }
//    }
//}

/*对象分解*，将Expr类的功能扩充，使得其子类都是以重写功能的形式存在，这样使得体系更加的明确/
/*问题比较多，Num强加了一些不属于它的功能，目的在于适配这一套体系*/
/*当Operate计算情况多种时，会导致Expr类中又引入不少的判断逻辑，增加复杂性*/
sealed class Expr {
    abstract fun isZero() : Boolean
    abstract fun isAddZero() : Boolean
    abstract fun left() : Expr
    abstract fun right() : Expr

    data class Num(val value: Int) : Expr() {
        override fun isZero(): Boolean = this.value == 0

        override fun isAddZero(): Boolean = false

        override fun left(): Expr = throw Throwable("no element")

        override fun right(): Expr = throw Throwable("no element")

    }


    data class Operate(val opName: String, val left: Expr, val right: Expr) : Expr() {
        override fun isZero(): Boolean = false

        override fun isAddZero(): Boolean = this.opName == "+" && (this.left.isZero() || this.right.isZero())

        override fun left(): Expr = this.left

        override fun right(): Expr = this.right
    }
}*/

/*采用访问者模式设计*/
sealed class Expr {
    abstract fun accept(visitor: Visitor) : Boolean

    data class Num(val value: Int) : Expr() {
        override fun accept(visitor: Visitor): Boolean = visitor.visit(this)

    }

    data class Operate(val opName: String, val left: Expr, val right: Expr) : Expr() {
        override fun accept(visitor: Visitor): Boolean = visitor.visit(this)
    }

    fun simplify(expr: Expr, visitor: Visitor) {

    }
}
class Visitor {
    fun visit(expr: Expr.Num) : Boolean = false

    fun visit(expr: Expr.Operate) : Boolean = true
}