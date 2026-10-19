package experiments;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class LeastRecentlyUsedCache<K, V> {
  private final int capacity;
  private final Map<K, Node<K, V>> entries = new HashMap<>();
  private final Node<K, V> head = new Node<>(null, null);
  private final Node<K, V> tail = new Node<>(null, null);

  public LeastRecentlyUsedCache(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("capacity must be positive");
    }
    this.capacity = capacity;
    head.next = tail;
    tail.prev = head;
  }

  public V get(K key) {
    Node<K, V> node = entries.get(key);
    if (node == null) {
      return null;
    }

    moveToFront(node);
    return node.value;
  }

  public void put(K key, V value) {
    Node<K, V> existing = entries.get(key);
    if (existing != null) {
      existing.value = value;
      moveToFront(existing);
      return;
    }

    if (entries.size() == capacity) {
      Node<K, V> leastRecentlyUsed = removeLast();
      entries.remove(leastRecentlyUsed.key);
    }

    Node<K, V> node = new Node<>(key, value);
    addFirst(node);
    entries.put(key, node);
  }

  public int size() {
    return entries.size();
  }

  private void moveToFront(Node<K, V> node) {
    remove(node);
    addFirst(node);
  }

  private void addFirst(Node<K, V> node) {
    insertAfter(head, node);
  }

  private void insertAfter(Node<K, V> anchor, Node<K, V> node) {
    Node<K, V> successor = anchor.next;

    anchor.next = node;
    node.prev = anchor;
    node.next = successor;
    successor.prev = node;
  }

  private void remove(Node<K, V> node) {
    Node<K, V> previous = node.prev;
    Node<K, V> next = node.next;

    previous.next = next;
    next.prev = previous;

    node.prev = null;
    node.next = null;
  }

  private Node<K, V> removeLast() {
    Node<K, V> node = tail.prev;
    remove(Objects.requireNonNull(node, "cache is internally inconsistent"));
    return node;
  }

  private static final class Node<K, V> {
    private final K key;
    private V value;
    private Node<K, V> prev;
    private Node<K, V> next;

    private Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }
}
