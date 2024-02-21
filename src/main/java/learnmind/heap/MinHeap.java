/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021-2022, Hamdi Douss
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package learnmind.heap;


/**
 * MinHeap (priority queue) data structure that supports value update for nodes.
 * @param <E> Node element type
 * @since 0.1
 */
public class MinHeap<E> {

    /**
     * Constant representing the first (popping) position in the array.
     */
    private static final int FRONT = 1;

    /**
     * Internal array to represent the heap.
     */
    private Node<E>[] heap;

    /**
     * Actual size of the heap.
     */
    private int size;

    /**
     * Max size supported by the heap.
     */
    private int max;

    /**
     * Constructor. Builds a heap with the passed maximum size.
     * @param initial Initial size supported by the heap
     */
    @SuppressWarnings("unchecked")
    public MinHeap(final int initial) {
        this.max = initial;
        this.size = 0;
        this.heap = new Node[this.max + 1];
        this.heap[0] = new Node<>(null, Integer.MIN_VALUE);
    }

    /**
     * Recalculates node position in the tree. This method is typically if the node value changes.
     * @param elt The element held by the node that should be updated in the tree
     */
    public void update(final E elt) {
        final int pos = this.position(elt);
        if (pos == MinHeap.FRONT) {
            return;
        }
        if (!this.minHeapify(pos)) {
            this.bubble(pos);
        }
    }

    /**
     * Gets the position of an element inside the internal array. 
     * @param elt Element to look for
     * @return Position index or -1 if not found
     */
    private int position(final E elt) {
        int result = -1;
        for (int idx = 1; idx <= this.size; ++idx) {
            if (this.heap[idx].element().equals(elt)) {
                result = idx;
                break;
            }
        }
        return result;
    }

    /**
     * Returns the tree node holding the element.
     * @param elt Element
     * @return Node
     */
    public Node<E> node(final E elt) {
        Node<E> result = null;
        int idx = this.position(elt);
        if (idx > -1) {
            result = this.heap[idx];
        }
        return result;
    }

    /**
     * Inserts a node in the heap.
     * @param element Element to insert
     */
    @SuppressWarnings("unchecked")
    public void insert(final Node<E> element) {
        if (this.size >= this.max) {
            this.max += 10;
            Node<E>[] newheap = new Node[this.max + 1];
            System.arraycopy(this.heap, 0, newheap, 0, this.max - 9);
            this.heap = newheap;
        }
        this.heap[++this.size] = element;
        this.bubble(this.size);
    }

    /**
     * Pops the minimum element.
     * @return The minimum element
     */
    public Node<E> peek() {
        Node<E> peeked = null;
        if (this.size > 0) {
            peeked = this.heap[MinHeap.FRONT];
        }
        return peeked;
    }

    /**
     * Checks if the passed position is a leaf.
     * @param pos Position to check
     * @return Boolean indicating if the position is a leaf
     */
    private boolean leaf(final int pos) {
        return pos > (this.size / 2) && pos <= this.size;
    }

    /**
     * Swaps nodes residing in the passed positions.
     * @param fpos First position
     * @param spos Second position
     */
    private void swap(final int fpos, final int spos) {
        final Node<E> tmp = this.heap[fpos];
        this.heap[fpos] = this.heap[spos];
        this.heap[spos] = tmp;
    }

    /**
     * Bubbles the element up as long as its value is lower than its parent's value.
     * @param pos Element position
     */
    private void bubble(final int pos) {
        int current = pos;
        while (this.heap[current].value() < this.heap[MinHeap.getParent(current)].value()) {
            this.swap(current, MinHeap.getParent(current));
            current = MinHeap.getParent(current);
        }
    }

    /**
     * Heapifies (pushes downward the tree) the element in the passed position.
     * @param pos Position of the element to heapify
     * @return True if the element was actually pushed downward
     */
    private boolean minHeapify(final int pos) {
        final int leftp = MinHeap.getLeft(pos);
        final int rightp = MinHeap.getRight(pos);
        final double value = this.heap[pos].value();
        final boolean parent = !this.leaf(pos);
        final boolean supleft = this.check(leftp, value, parent);
        final boolean supright = this.check(rightp, value, parent);
        boolean pushed = false;
        if (supleft || supright) {
            pushed = true;
            if (supleft && (!supright || this.heap[leftp].value() < this.heap[rightp].value())) {
                this.swap(pos, leftp);
                this.minHeapify(leftp);
            } else {
                this.swap(pos, rightp);
                this.minHeapify(rightp);
            }
        }
        return pushed;
    }

    /**
     * Checks if node value is greater than a potentiel child value.
     * @param child Child position
     * @param value Current node value
     * @param parent Whether this node is a parent
     * @return Whether the node has a potential child with a smaller value
     */
    private boolean check(final int child, final double value, final boolean parent) {
        return parent && child <= this.size && this.heap[child] != null
            && value > this.heap[child].value();
    }

    /**
     * Returns the position of the parent.
     * @param pos Position of child
     * @return Position of parent
     */
    private static int getParent(final int pos) {
        return pos / 2;
    }

    /**
     * Returns the position of the left child.
     * @param pos Position of parent
     * @return Position of left child
     */
    private static int getLeft(final int pos) {
        return 2 * pos;
    }

    /**
     * Returns the position of the right child.
     * @param pos Position of parent
     * @return Position of right child
     */
    private static int getRight(final int pos) {
        return 2 * pos + 1;
    }
}
