package com.example.activityTransitiondemo

import com.example.activityTransitiondemo.RevertLinkedNode.ListNode
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("------")
        assertEquals(4, 2 + 2)
        val node1 = RevertLinkedNode().ListNode(10)
        val node2 = RevertLinkedNode().ListNode(5)
        val node3 = RevertLinkedNode().ListNode(7)
        node1.next =node2
        node2.next =node3
        var reverseList = RevertLinkedNode().ReverseList(node1)
        var temp = reverseList
        while (temp!=null){
            println(temp.`val`)
            temp = temp.next
        }
    }
}