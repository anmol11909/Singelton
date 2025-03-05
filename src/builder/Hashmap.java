package builder;

import javax.sound.sampled.Line;
import java.util.LinkedList;
import java.util.List;


public class Hashmap <K,V> {
    public class HMNode {
        K key;
        V value;

        HMNode(K key, V value) {
            this.key = key;
            this.value = value;

        }
    }

    //create an array of Linkedlist - int[] arr = new int[5]

       private LinkedList<HMNode>[] buckets;
       private int currSize = 0;
        public Hashmap(){
            this.currSize = 0;
            buckets = new LinkedList[10];
            for(int i=0; i<buckets.length; i++){
                buckets[i] = new LinkedList<>();
            }
        }
        public void put(K key, V value){
            // Step 1 - get the hashValue of the key using hashFunction
            int  bucketIndex = hashFunction(key);
            HMNode curr = find(buckets[bucketIndex], key);
            if(curr!=null){
                curr.value = value; // update the value
            }
            else{
                // create a new node
                HMNode node = new HMNode(key, value);
                buckets[bucketIndex].addFirst(node);
                this.currSize++;
            }

            // now check if lambda load factor is in control

            Double lambda = (double)this.currSize/buckets.length;
            if(lambda>2){// create a new array and deep copy
                reHash();
            }
        }

        public V get(K key) {
            int bucketIndex = hashFunction(key); // getting the index
            HMNode curr = find(buckets[bucketIndex], key); // finding the Node
            if (curr != null) {
                return curr.value;
            }
            return null;
        }

        public void remove(K key) {
            int bucketIndex = hashFunction(key); // getting the index
            HMNode curr = find(buckets[bucketIndex], key); // finding the Node
            if (curr != null) {
                buckets[bucketIndex].remove(curr);
            }
        }
        public int size() {
            return this.currSize;
        }

        private void reHash(){
            // create a new array and deep copy

            LinkedList<HMNode>[] oldBuckets = buckets;
            buckets = new LinkedList[oldBuckets.length * 2];
            for(int i=0; i<oldBuckets.length; i++){
                LinkedList<HMNode> curr = oldBuckets[i];
                // in curr we have the LL , now we will traverse it
                for(int j=0; j<curr.size(); j++){
                    HMNode node = curr.get(j);
                    this.put(node.key, node.value);  // this refers to current object of HashMap
                }
            }
        }


    private int hashFunction(K key) {
            int ind = key.hashCode(); // now this can be -ve as well
            ind = Math.abs(ind);
            ind = ind % buckets.length;
            return ind;

        }

        private HMNode find(LinkedList<HMNode> bucket, K key){
            for(int i=0; i<bucket.size(); i++){
                if(bucket.get(i).key.equals(key)){
                    return bucket.get(i);
                }
            }
            return null;
        }

    }