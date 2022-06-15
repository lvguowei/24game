package com.guowei.twentyfourgame

import kotlin.math.abs

fun twentyFour(numbers: List<Int>): Set<Node> {
    val result = mutableSetOf<Node>()
    solve(numbers.map { LeafNode(it.toDouble()) }) {
        result.add(it)
    }
    return result
}

sealed class Node {
    abstract fun calc(): Double?
}

data class CompoundNode(val op: OP, val left: Node, val right: Node) : Node() {

    override fun calc(): Double? {
        val l = left.calc()
        val r = right.calc()
        if (l == null || r == null) return null

        return when (op) {
            OP.PLUS -> l + r
            OP.MINUS -> l - r
            OP.MUL -> l * r
            OP.DIV -> if (r == 0.0) null else l / r
        }
    }

    override fun equals(other: Any?): Boolean {
        return if (other is CompoundNode) {
            return if (this.op == other.op) {
                when (this.op) {
                    OP.PLUS,
                    OP.MUL -> (this.left == other.left && this.right == other.right) || (this.left == other.right && this.right == other.left)
                    OP.MINUS,
                    OP.DIV -> this.left == other.left && this.right == other.right
                }
            } else {
                false
            }
        } else {
            false
        }
    }

    override fun toString(): String {
        return when (op) {
            OP.PLUS -> "($left ➕ $right)"
            OP.MINUS -> "($left ➖ $right)"
            OP.MUL -> "($left ✖ $right)"
            OP.DIV -> "($left ➗ $right)"
        }
    }


    override fun hashCode(): Int {
        val result = op.hashCode()
        when (op) {
            OP.PLUS,
            OP.MUL -> 31 * result + left.hashCode() + right.hashCode()
            OP.MINUS,
            OP.DIV -> 31 * (31 * result + left.hashCode()) + right.hashCode()
        }
        return result
    }
}

data class LeafNode(val value: Double) : Node() {

    override fun calc(): Double? {
        return value
    }

    override fun equals(other: Any?): Boolean {
        return if (other is LeafNode) {
            this.value == other.value
        } else {
            false
        }
    }

    override fun toString(): String {
        return "${value.toInt()}"
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

enum class OP {
    PLUS, MINUS, MUL, DIV
}

fun solve(nodes: List<Node>, found: (Node) -> Unit) {
    if (nodes.size == 1) {
        if (abs((nodes.first().calc() ?: 0.0) - 24.0) < 0.000001) {
            found(nodes.first())
        }
    } else {
        val indexes = (nodes.indices).toSet()
        for (i in nodes.indices) {
            for (j in nodes.indices) {
                if (i != j) {
                    for (op in OP.values()) {
                        val result = mutableListOf<Node>()
                        val rest = indexes.minus(setOf(i, j))
                        result.add(CompoundNode(op, nodes[i], nodes[j]))
                        for (r in rest) {
                            result.add(nodes[r])
                        }
                        solve(result, found)
                    }
                }
            }
        }
    }
}
