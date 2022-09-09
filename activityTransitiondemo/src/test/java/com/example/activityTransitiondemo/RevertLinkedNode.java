package com.example.activityTransitiondemo;

public class RevertLinkedNode {

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode ReverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode preNode=null;
        ListNode nextNode=null;
        while (head.next != null) {
            ListNode next_head = head.next;
            head.next = preNode;
            preNode=head;
            head=next_head;
        }
        return head;
    }



}
